package com.miaoshaproject.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author wangshuo
 * @Date 2022/5/9, 10:23
 * Java提供的原子操作类
 * AtomicInteger对象实现了CAS，所以加不加锁都没有出现脏读幻读重复读
 */
public class CAS_Demo {

    public /*synchronized*/ static void main(String[] args) {
        /*
        AtomicLong atomicLong = new AtomicLong();
         */
        //创建Atomic原子操作对象
        //ReentrantLock lock = new ReentrantLock();
        AtomicInteger atomicInteger = new AtomicInteger();

        //lock.lock();
        //开启五个线程，每个线程执行十次自增
        for (int i = 0; i < 5; i++) {

            new Thread(() -> {

                for (int j = 0; j < 10; j++) {
                    //自增并输出
                    System.out.println(atomicInteger.incrementAndGet());
                }
            }).start();
        }
        //lock.unlock();
    }
}
