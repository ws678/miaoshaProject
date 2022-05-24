package com.miaoshaproject.demo;

/**
 * @Author wangshuo
 * @Date 2022/5/9, 11:29
 * volatile应用场景
 * 需要保证可见性但不需要保证原子性时，可以使用volatile关键字，它可以保证可见性，而且性能消耗更少
 */
public class Volatile_Demo {

    private static volatile boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException {

        //开启一个线程Run
        new Thread(() -> {

            while (isRunning) {
                System.out.println("Running");
            }
        }).start();

        //休息两秒之后调用停止方法
        Thread.sleep(2000);
        stopWhile();
    }

    private static void stopWhile() {

        isRunning = false;
    }
}
