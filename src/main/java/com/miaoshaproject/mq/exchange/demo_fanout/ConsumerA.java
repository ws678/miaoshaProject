package com.miaoshaproject.mq.exchange.demo_fanout;

import com.miaoshaproject.mq.util.RabbitMQConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author wangshuo
 * @Date 2022/5/7, 13:36
 * Please add a comment
 */
public class ConsumerA {

    private static final String EXCHANGE_NAME = "fanout_exchange";
    private static final String A_QUEUE_NAME = "queueA";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        //关联交换机与队列
        channel.queueBind(A_QUEUE_NAME, EXCHANGE_NAME, "");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            //重写收到消息后的动作
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("消费者A收到消息： " + msg);
            }
        };
        //绑定队列，监听消息并自动删除已收到的消息
        channel.basicConsume(A_QUEUE_NAME, true, defaultConsumer);
    }
}
