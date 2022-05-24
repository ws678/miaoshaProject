package com.miaoshaproject.mq.demo_rumen;

import com.miaoshaproject.mq.util.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author wangshuo
 * @Date 2022/5/6, 16:57
 * Please add a comment
 */
public class RabbitMQProducer extends Thread {

    private static final String QUEUE_NAME = "queue1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //demo1
        //test1();

        //使用消息确认机制
        //test2();

        //使用事务机制
        //test3();

        //开启五个线程分别生产100条消息
        test4();
    }

    //继承Thread类重写run方法
    @Override
    public synchronized void run() {

        try {
            //创建连接
            Connection connection = RabbitMQConnection.getConnection();
            //创建通道
            Channel channel = connection.createChannel();
            channel.txSelect();
            try {
                //发送一百条消息
                for (int i = 0; i < 100; i++) {

                    String msg = Thread.currentThread().getName() + "发送消息：消息测试" + i;
                    System.out.println(msg);
                    channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                }
                channel.txCommit();
            } catch (Exception e) {
                //打印错误信息，回滚
                System.out.println(e);
                channel.txRollback();
            } finally {
                //关闭通道和连接
                channel.close();
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void test4() throws IOException, TimeoutException {

        RabbitMQProducer rmqp = new RabbitMQProducer();
        new Thread(rmqp,"线程1").start();
        new Thread(rmqp,"线程2").start();
        new Thread(rmqp,"线程3").start();
        new Thread(rmqp,"线程4").start();
        new Thread(rmqp,"线程5").start();
    }

    private static void test3() throws IOException, TimeoutException {

        //创建连接
        Connection connection = RabbitMQConnection.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        channel.txSelect();
        try {
            //发送消息
            String msg = "消息测试1";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            channel.txCommit();
        } catch (Exception e) {
            //打印错误信息，回滚
            System.out.println(e);
            channel.txRollback();
        } finally {
            //关闭通道和连接
            channel.close();
            connection.close();
        }
    }

    private static void test2() throws IOException, TimeoutException, InterruptedException {

        //创建连接
        Connection connection = RabbitMQConnection.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        String msg = "消息测试2";
        //Select Confirms
        channel.confirmSelect();
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        //消息确认
        boolean b = channel.waitForConfirms();
        if (b)
            System.out.println("投递成功");
        else
            System.out.println("投递失败");//抛出错误
        //关闭通道和连接
        channel.close();
        connection.close();
    }

    private static void test1() throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMQConnection.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        String msg = "消息测试1";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
