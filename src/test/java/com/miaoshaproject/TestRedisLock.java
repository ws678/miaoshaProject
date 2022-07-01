package com.miaoshaproject;

import com.miaoshaproject.util.lock.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;

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

        //初始化LockKey
        redisLock.setKey("KeyTest");
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
            num--;
            return true;
        } else
            return false;
    }
}
