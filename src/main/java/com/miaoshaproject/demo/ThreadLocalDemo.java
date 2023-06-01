package com.miaoshaproject.demo;

/**
 * @description: ThreadLocalTest
 * @author: wangshuo
 * @date: 2023-04-25 19:16:59
 */
public class ThreadLocalDemo {

    private static EntryDemo entryDemo = new EntryDemo();

    public static void main(String[] args) {


        Thread t1 = new Thread(() -> {
            //创建线程一
            //  模拟数据存入：每隔300毫秒存入一条数据
            for (int i = 0; i < 10; i++) {

                try {
                    Thread.sleep(300);
                    entryDemo.setStr1("线程一存入：" + i);
                    entryDemo.setTl("线程一存入：" + i);
                    //  模拟数据读取：每隔300毫秒读取一次
                    test();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "线程一");

        Thread t2 = new Thread(() -> {
            //创建线程二
            //  模拟数据存入：每隔300毫秒存入一条数据
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(300);
                    entryDemo.setStr1("线程二存入：" + i);
                    entryDemo.setTl("线程二存入：" + i);
                    //  模拟数据读取：每隔300毫秒读取一次
                    test();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "线程二");

        //同时启动
        t1.start();
        t2.start();

        //测试结果
        //  两个线程共输出二十次内容 str1出现了脏读的现象 tl全部正确
    }

    //测试 加上sync锁
    private synchronized static void test() {
        String threadName = Thread.currentThread().getName();
        String subStr1 = entryDemo.getStr1().substring(0, 3);
        String subTl = entryDemo.getTl().substring(0, 3);
        if (threadName.equals(subStr1) && threadName.equals(subTl)) {

            System.out.println(threadName + "：本次读取未出现脏读");
        } else if (!threadName.equals(subTl)) {

            System.out.println(threadName + "：tl出现脏读现象");
        } else if (!threadName.equals(subStr1)) {

            System.out.println(threadName + "：str1出现脏读现象");
        }
        //输出一下
        System.out.println("-----------------------------------------------------------------------------------------\n str1："
                + entryDemo.getStr1() + "\n tl：" + entryDemo.getTl() + "\n");

        entryDemo.tlRemove(); //用完之后进行Remove 帮助jvm进行垃圾回收 尤其是在线程池中使用之后一定要remove
    }

    public static class EntryDemo {

        private String str1;
        private ThreadLocal<String> tl = new ThreadLocal<String>();

        public String getStr1() {
            return str1;
        }

        public void setStr1(String str1) {
            this.str1 = str1;
        }

        public String getTl() {
            return tl.get();
        }

        public void setTl(String str2) {
            tl.set(str2);
        }

        public void tlRemove() {
            this.tl.remove();
        }
    }
}
