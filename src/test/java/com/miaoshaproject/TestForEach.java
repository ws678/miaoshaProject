package com.miaoshaproject;

import org.joda.time.Instant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;

/**
 * @Author wangshuo
 * @Date 2022/7/1, 13:57
 * @Description: 阿里巴巴操作手册内容
 * jdk1.8新功能：map.forEach
 * 原子操作类 / 时间操作类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestForEach {

    /*
        使用forEach来遍历map
        但是hashSet好像也给自动排序了 百度说是类似hash冲突重构了
     */
    @Test
    public void a() throws InterruptedException {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> hashMap = new HashMap<>();
        HashSet<String> hashSet = new HashSet<>();
        TreeSet<String> treeSet = new TreeSet<>();
        hashMap.put("4", "3");
        hashMap.put("1", "1");
        hashMap.put("6", "2");
        hashMap.put("2", "8");
        hashMap.put("3", "1");
        //普通遍历
        hashMap.forEach((s, s2) -> {
            map.put(s, s2);
        });
        System.out.println("map：" + map);
        //放入set自动去重
        hashMap.forEach((s, s2) -> {
            hashSet.add(s2);
            treeSet.add(s2);
        });
        System.out.println("hashSet：" + hashSet);
        System.out.println("treeSet：" + treeSet);
        //线程安全的原子操作类。 Since 1.8 ， 采用分段锁技术 比Atomic效率要高
        LongAdder longAdder = new LongAdder();
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                longAdder.increment();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                longAdder.increment();
            }
        }).start();
        //睡眠一下 等线程执行完
        Thread.sleep(3000);
        System.out.println(longAdder);
        //时间操作类。 Since 1.8 ， 效率更高
        Instant instant = new Instant();
        System.out.println(instant.getMillis());
    }
}
