package com.miaoshaproject.mq.demo_shouxie;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author wangshuo
 * @Date 2022/5/4, 21:42
 * 基于多线程搭建简单MQ
 */
public class ThreadMQ {

    private static LinkedBlockingDeque<JSONObject> mq = new LinkedBlockingDeque<JSONObject>();

    public static void main(String[] args) {

        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", "wangshuo");
                        mq.offer(jsonObject);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者");

        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        JSONObject poll = mq.poll();
                        if (poll != null) {
                            System.out.println(poll.get("name"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "消费者");

        producerThread.start();
        consumerThread.start();
    }
}
