package com.miaoshaproject.demo;

import java.util.concurrent.*;

/**
 * @Author wangshuo
 * @Date 2022/5/12, 12:55
 * 为什么要使用线程池：
 * 诸如 Web 服务器、数据库服务器、文件服务器或邮件服务器之类的许多服务器应用程序都面向处理来自某些远程来源的大量短小的任务。
 * 请求以某种方式到达服务器，这种方式可能是通过网络协议（例如 HTTP、FTP 或 POP）、通过 JMS 队列或者可能通过轮询数据库。
 * 不管请求如何到达，服务器应用程序中经常出现的情况是：单个任务处理的时间很短而请求的数目却是巨大的。
 * 构建服务器应用程序的一个简单模型是：每当一个请求到达就创建一个新线程，然后在新线程中为请求服务。
 * 实际上对于原型开发这种方法工作得很好，但如果试图部署以这种方式运行的服务器应用程序，那么这种方法的严重不足就很明显。
 * 每个请求对应一个线程（thread-per-request）方法的不足之一是：为每个请求创建一个新线程的开销很大；
 * 为每个请求创建新线程的服务器在创建和销毁线程上花费的时间和消耗的系统资源要比花在处理实际的用户请求的时间和资源更多。
 * 除了创建和销毁线程的开销之外，活动的线程也消耗系统资源。
 * 在一个 JVM 里创建太多的线程可能会导致系统由于过度消耗内存而用完内存或“切换过度”。
 * 为了防止资源不足，服务器应用程序需要一些办法来限制任何给定时刻处理的请求数目。
 * 线程池为线程生命周期开销问题和资源不足问题提供了解决方案。
 * 通过对多个任务重用线程，线程创建的开销被分摊到了多个任务上。
 * 其好处是，因为在请求到达时线程已经存在，所以无意中也消除了线程创建所带来的延迟。
 * 这样，就可以立即为请求服务，使应用程序响应更快。
 * 而且，通过适当地调整线程池中的线程数目，也就是当请求的数目超过某个阈值时，就强制其它任何新到的请求一直等待，
 * 直到获得一个线程来处理为止，从而可以防止资源不足。
 * <p>
 * <p>
 * 常用的几种线程池demo
 */
public class ThreadPoolDemo {

    //newCachedThreadPool 可缓存线程池，可灵活回收空闲线程
    /*
        特点：工作线程的创建数量几乎没有限制(其实也有限制的,数目为Integer.MAX_VALUE), 这样可灵活的往线程池中添加线程。
        如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。
        终止后，如果你又提交了新的任务，则线程池重新创建一个工作线程。

        在使用CachedThreadPool时，一定要注意控制任务的数量，否则，由于大量线程同时运行，很有会造成系统瘫痪。
     */
    public static class newCachedClass {

        public static void main(String[] args) {
            ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
            for (int i = 0; i < 10; i++) {
                final int index = i;
                try {
                    Thread.sleep(index * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newCachedThreadPool.execute(() -> {
                    System.out.println(index);
                });
            }
        }
    }

    //newFixedThreadPool 创建一个指定工作线程数量的线程池。
    /*
        特点：每当提交一个任务就创建一个工作线程，如果工作线程数量达到线程池初始的最大数，则将提交的任务存入到池队列中。
        它具有线程池提高程序效率和节省创建线程时所耗的开销的优点。
        但是，在线程池空闲时，即线程池中没有可运行任务时，它不会释放工作线程，还会占用一定的系统资源。
     */
    public static class newFixedClass {

        public static void main(String[] args) {
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);//初始最大数
            for (int i = 0; i < 30; i++) {
                final int index = i;
                newFixedThreadPool.execute(() -> {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    //newSingleThreadExecutor 顺序执行线程池
    /*
        特点：
        创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务。
        它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO,优先级)执行。
        如果这个线程异常结束，会有另一个取代它，保证顺序执行。
        单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。
     */
    public static class newSingleClass {

        public static void main(String[] args) {
            ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
            for (int i = 0; i < 10; i++) {
                final int index = i;
                newSingleThreadExecutor.execute(() -> {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    //newScheduledThreadPool 特点：定长的线程池，支持定时任务以及周期性任务执行
    public static class newScheduleClass {

        public static void main(String[] args) {

            ScheduledExecutorService newScheduledThreadPool =
                    Executors.newScheduledThreadPool(5);//线程池长度
            //延迟三秒后执行
            demoSchedule(newScheduledThreadPool);
            //延迟一秒后，每三秒执行一次
            demoScheduleAtFixedRate(newScheduledThreadPool);
        }

        private static void demoSchedule(ScheduledExecutorService newScheduledThreadPool){

            newScheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("delay 3 seconds");
                }
            }, 3, TimeUnit.SECONDS);
        }

        private static void demoScheduleAtFixedRate(ScheduledExecutorService newScheduledThreadPool){

            newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println("delay 1 seconds , and execute every 3 seconds");
                }
            }, 1, 3, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(5);
    }
}
