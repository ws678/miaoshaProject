package com.miaoshaproject.mq.exchange.demo_fanout;

import com.miaoshaproject.mq.util.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author wangshuo
 * @Date 2022/5/7, 13:23
 * Please add a comment
 */
public class Producer {

    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        //通道关联交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true);
        String msg = "测试fanout交换机";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
