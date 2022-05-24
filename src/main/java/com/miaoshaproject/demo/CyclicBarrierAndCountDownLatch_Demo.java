package com.miaoshaproject.demo;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Author wangshuo
 * @Date 2022/5/17, 18:17
 * CyclicBarrier / CountDownLatch 小demo
 */
public class CyclicBarrierAndCountDownLatch_Demo {

    /*
        CyclicBarrier多个线程相互等待，全部执行完毕await()后一起继续运行
     */
    static class CyclicBarrierDemo implements Runnable {

        //私有CyclicBarrier变量
        private CyclicBarrier cyclicBarrier;
        private String name;

        //提供构造方法
        public CyclicBarrierDemo(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        //重写run方法
        @Override
        public void run() {
            try {
                //让三个线程随机睡眠1-10秒
                Thread.sleep(1000 * new Random().nextInt(10));
                System.out.println(name + "睡醒了 开始等待其他线程");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(name + " 出发");
        }
    }

    //测试CyclicBarrierDemo
    public static class Race {

        public static void main(String[] args) {
            //指定三个线程
            CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
            //创建一个指定数量的线程池
            ExecutorService executor = Executors.newFixedThreadPool(3);
            executor.submit(new Thread(new CyclicBarrierDemo(cyclicBarrier, "张三")));
            executor.submit(new Thread(new CyclicBarrierDemo(cyclicBarrier, "李四")));
            executor.submit(new Thread(new CyclicBarrierDemo(cyclicBarrier, "王五")));
            //程序执行完毕，卸磨杀驴
            executor.shutdown();
        }
    }

    /*
        CountDownLatch创建时可以指定计数器大小
            线程调用await()方法后会
                等待其他线程执行CountDown()将计数器减少至0后再进行执行
            线程调用CountDown()方法后会
                将CountDownLatch的计数器减一
     */
    static class CountDownLatchDemo {

        private static final int N = 10;

        static class WorkerDemo implements Runnable {

            private final CountDownLatch doneSignal;
            private final CountDownLatch startSignal;
            private int beginIndex;

            //对外提供构造方法
            public WorkerDemo(CountDownLatch doneSignal, CountDownLatch startSignal, int beginIndex) {
                this.doneSignal = doneSignal;
                this.startSignal = startSignal;
                this.beginIndex = beginIndex;
            }

            @Override
            public void run() {

                try {
                    startSignal.await();//因为我们先写的创建线程的方法，为防止线程先执行代码，我们先进行阻塞
                    for (int i = 0; i < beginIndex; i++) {
                        //想要同步执行可以传一个锁进来在这里lock，在下边unlock
                        System.out.println(Thread.currentThread().getName().concat(": ") + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    doneSignal.countDown();//十个线程都countDown()之后主线程的锁就会解开，随后打印“执行完毕”
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {

            CountDownLatch doneSignal = new CountDownLatch(N);
            CountDownLatch startSignal = new CountDownLatch(1);
            //异步创建十个WorkerDemo线程 让它们分别打印1到十个数字
            for (int i = 1; i <= N; i++) {
                new Thread(new WorkerDemo(doneSignal, startSignal, i), "Thread" + i).start();
            }
            startSignal.countDown();//这里打开了开关,十个线程一起执行代码
            System.out.println("开始执行");
            doneSignal.await();//这里锁上了
            System.out.println("执行完毕");
        }
    }
}
