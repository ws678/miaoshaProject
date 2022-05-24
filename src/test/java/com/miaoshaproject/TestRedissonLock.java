package com.miaoshaproject;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wangshuo
 * @Date 2022/5/15, 8:52
 * Redisson实现分布式锁：缺点是资源消耗较大
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class TestRedissonLock {

    //库存数量
    private static int num = 3;
    //锁对象
    private static Lock lock = new ReentrantLock();
    //Redisson锁
    private static RLock lock1;

    public static void main(String[] args) {
        /*TestRedissonLock te = new TestRedissonLock();
        System.out.println(te.getClass().getName());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());*/
        stockThread();
    }

    public static void stockThread() {

        set();

        for (int i = 0; i < 5; i++) {

            new Thread(() -> {

                lock1.lock();
                //调用减少库存的方法
                boolean b = reduceStock();
                lock1.unlock();
                if (b)
                    System.out.println(Thread.currentThread().getName() + "减少库存成功");
                else
                    System.out.println(Thread.currentThread().getName() + "减少库存失败");
            }, "Thread" + i).start();
        }
    }

    //减少库存的方法
    public static boolean reduceStock() {
        if (num > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num--;
            return true;
        } else
            return false;
    }

    public static void set() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        RedissonClient redissonClient = Redisson.create(config);
        lock1 = redissonClient.getLock("lock1");
    }
}
