package com.miaoshaproject.mq.demo_rumen;

import com.miaoshaproject.mq.util.RabbitMQConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author wangshuo
 * @Date 2022/5/6, 17:05
 * Please add a comment
 */
public class RabbitMQConsumer {

    private static final String QUEUE_NAME = "queue1";

    public static void main(String[] args) throws IOException, TimeoutException {

        Thread thread1 = new Thread(new Runnable(){

            @Override
            public synchronized void run() {
                try {
                    //开启消费者
                    consumer("线程一");
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable(){

            @Override
            public synchronized void run() {
                try {
                    //开启消费者
                    consumer("线程二");
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
    }

    private static void consumer(String a) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMQConnection.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //自定义一个Consumer
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {

            //重写获取后动作
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String s = new String(body, "UTF-8");
                System.out.println(a + "消费者收到消息： " + s);
                //消费完成，通知MQ删除消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //监听队列 第二个参数autoAck-true：自动签收消息 一般我们会设置为false
        //channel.basicConsume(QUEUE_NAME, true, defaultConsumer);

        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }
}
