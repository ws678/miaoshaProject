package com.miaoshaproject.util.lock;

import com.miaoshaproject.util.redis.RedisUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wangshuo
 * @Date 2022/5/15, 15:16
 * Please add a comment
 */
@Component
public class RedisLock implements Lock {

    @Autowired
    RedisTemplate redisTemplate;
    private String key;

    //对外提供私有key变量赋值
    //这里使用了@Component对外提供Bean，
    //  我们也可以使用装饰器设计模式，使需要用锁的类继承RedisLock类，重写lock和unlock方法
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void lock() {

        while (true) {

            //分布式事务保证安全性，使用redis作为锁标识
            //每个key都要不一样
            boolean setnx = redisTemplate.opsForValue().setIfAbsent(key, "Value", 5,TimeUnit.SECONDS);//设置5秒过期
            if (setnx)
                return;
            else
                System.out.println(Thread.currentThread().getName() + "等待中");
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

        redisTemplate.delete("LockName");
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
