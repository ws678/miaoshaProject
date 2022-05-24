package com.miaoshaproject.login_config;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author wangshuo
 * @Date 2022/4/24, 12:10
 * Jedis操作redis
 */
@Component
public class JedisClientConfig {

    public static JedisPool getJedisPool(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大连接空闲数
        jedisPoolConfig.setMaxIdle(30);
        //最大连接数
        jedisPoolConfig.setMaxTotal(100);
        return new JedisPool(jedisPoolConfig,"127.0.0.1",6379);
    }

    public static Jedis getJedis(JedisPool jedisPool){
        Jedis jedis = jedisPool.getResource();
        //jedis.auth("678678");
        return jedis;
    }

    public static void closeJedisAndJedisPool(JedisPool jedisPool,Jedis jedis){

        jedis.close();
        jedisPool.close();
    }
}
