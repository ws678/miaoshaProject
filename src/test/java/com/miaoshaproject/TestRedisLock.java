package com.miaoshaproject;

import com.miaoshaproject.util.lock.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author wangshuo
 * @Date 2022/5/15, 20:02
 * 跑不起来的demo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisLock {
    //库存数量
    private static int num = 3;
    @Autowired
    private RedisLock redisLock;

    @Test
    public void stockThread() {

        for (int i = 0; i < 5; i++) {

            new Thread(() -> {

                redisLock.lock();
                //调用减少库存的方法
                boolean b = reduceStock();
                redisLock.unlock();
                if (b)
                    System.out.println(Thread.currentThread().getName() + "减少库存成功");
                else
                    System.out.println(Thread.currentThread().getName() + "减少库存失败");
            }, "Thread" + i).start();
        }
    }

    //减少库存的方法
    public boolean reduceStock() {
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
}
