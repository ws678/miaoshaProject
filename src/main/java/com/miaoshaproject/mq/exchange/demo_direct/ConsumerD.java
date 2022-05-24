package com.miaoshaproject.mq.exchange.demo_direct;

import com.miaoshaproject.mq.util.RabbitMQConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author wangshuo
 * @Date 2022/5/7, 14:08
 * Please add a comment
 */
public class ConsumerD {

    private static final String EXCHANGE_NAME = "direct_exchange";
    private static final String B_QUEUE_NAME = "queueD";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        //关联交换机、队列、路由key
        channel.queueBind(B_QUEUE_NAME, EXCHANGE_NAME, "sms");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {

            //重写收到消息后的动作
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String msg = new String(body, "UTF-8");
                System.out.println("消费者D收到消息： " + msg);
            }
        };
        channel.basicConsume(B_QUEUE_NAME, true, defaultConsumer);
    }
}
