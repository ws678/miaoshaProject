package com.miaoshaproject.util.lock;

import org.openjdk.jol.info.ClassLayout;

/*
    锁升级测试类
 */
public class LockUpgradeTest {

    public static void main(String[] args) throws InterruptedException {

        //Java程序启动 创建出了一个无锁的对象
        A aTemp = new A();
        System.out.println("无状态 （001）" + ClassLayout.parseInstance(aTemp).toPrintable());

        /*
            jvm默认延时四秒开启偏向锁 可通过 -XX:BiasedLockingStartupDelay = 0 取消延时
            如果不需要偏向锁 可通过 -XX:UseBiasedLocking = false来设置
         */
        Thread.sleep(5000);
        //Java启动超过五秒 之后创建的对象都默认启用偏向锁
        A a = new A();
        System.out.println("启用偏向锁（101）" + ClassLayout.parseInstance(a).toPrintable());

        //两次进入循环的结果一样 因为目前一直只有主线程在操作a对象
        for (int i = 0; i < 2; i++) {
            synchronized (a) {
                //sync锁住a对象 在对象中加入了主线程的id
                System.out.println("偏向锁（101） 带id " + ClassLayout.parseInstance(a).toPrintable());
            }
            //sync结束了 但是偏向锁并不会释放
            System.out.println("偏向锁释放（101） 带id " + ClassLayout.parseInstance(a).toPrintable());
        }

        new Thread(() -> {
            //新线程和主线程两个线程同时锁住了a对象 马上升级成为轻量级锁
            synchronized (a) {
                System.out.println("轻量级锁（00）" + ClassLayout.parseInstance(a).toPrintable());
                //睡眠三秒的过程中 第三个线程创建了 ↓ 这时候第二个线程还在锁住a对象没有释放 自旋了n次 此时a的锁升级为重量级锁
                System.out.println("睡眠三秒钟");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("轻量 -》 重量" + ClassLayout.parseInstance(a).toPrintable());
            }
        }).start();

        new Thread(() -> {
            //最后还是重量级锁 不会降级
            synchronized (a) {
                System.out.println("重量级锁（10）" + ClassLayout.parseInstance(a).toPrintable());
            }
        }).start();
    }
}