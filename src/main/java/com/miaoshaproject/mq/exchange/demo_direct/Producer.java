package com.miaoshaproject.mq.exchange.demo_direct;

import com.miaoshaproject.mq.util.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author wangshuo
 * @Date 2022/5/7, 14:05
 * Please add a comment
 */
public class Producer {

    private static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMQConnection.getConnection();
        Channel channel = connection.createChannel();
        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        String msg = "测试direct交换机";
        //指定key发送消息
        channel.basicPublish(EXCHANGE_NAME, "sms", null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
