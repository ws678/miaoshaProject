package com.miaoshaproject.util.lock;

public class LockTest {

    public static void main(String[] args) throws InterruptedException {

        A a = new A();
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000000; i++) {
                a.incr();
            }
        });
        //t1线程启动 此时t1线程开始执行 主线程会继续运行
        t1.start();

        for (int i = 0; i < 10000000; i++) {
            a.incr();
        }
        //t1线程插队 获取cpu执行权限 主线程后续代码需等待t1线程执行完毕
        t1.join();

        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
        System.out.println(a.getNum());

        long b = System.currentTimeMillis();
        while (a.getNum() < 50000000) {
            a.incr();
        }
        long c = System.currentTimeMillis();
        System.out.println(c - b);
        System.out.println(a.getNum());
    }
}
