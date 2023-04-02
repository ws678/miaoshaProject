package com.miaoshaproject.stream;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class a01_TestStream {

    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();
        Collections.addAll(strings, "张三丰", "周芷若", "赵敏", "老顽童", "张无忌", "张狗");

        //拿到所有姓张的 名字长度是三个字 打印这些数据
        strings.stream().filter((s) -> {

            return s.startsWith("张");
        }).filter((s) -> {

            return s.length() == 3;
        }).forEach(System.out::println);

        //map跟Collection没有关系 怎末获取流呢
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("k", "v");
        Stream<Object> stream = objectObjectHashMap.keySet().stream();
        Stream<Object> stream1 = objectObjectHashMap.values().stream();
        Stream<Map.Entry<Object, Object>> stream2 = objectObjectHashMap.entrySet().stream();

        //通过Stream中的静态方法of获取流
        Stream<String> v1 = Stream.of("v1", "v2", "v3");
        String[] aa = {"a", "b", "c"};
        Stream<String> aa1 = Stream.of(aa);

        //forEach方法
        Arrays.stream(aa).forEach(System.out::println);

        //count方法
        System.out.println(Arrays.stream(aa).count());

        //limit方法
        Arrays.stream(aa).limit(2).forEach(System.out::println);

        //skip(num) 跳过前num个元素
        Arrays.stream(aa).skip(1).forEach(System.out::println);

        //将Stream流中的Integer转化为Long类型
        Stream<Integer> v2 = Stream.of(99, 7, 6, 71, 2);
        v2.map(Integer::toUnsignedLong).forEach((v) -> {
            System.out.println(v + " " + v.getClass());
        });

        //sorted排序
        Stream<Integer> v3 = Stream.of(99, 7, 6, 71, 2);
        v3.sorted().forEach(System.out::println); //自动由小到大
        Stream<Integer> v4 = Stream.of(999, 7, 6, 71, 2);
        v4.sorted((i1, i2) -> {
            return i2 - i1; // 自定义比较器 需返回int类型
        }).forEach(System.out::println);

        //distinct去重
        Stream<Integer> integerStream = Stream.of(11, 11, 25, 36, 25, 6);
        integerStream.distinct().forEach(System.out::println);

        //match---
        //all match 流中的元素是否都满足Predicate内置接口中的方法 返回Boolean
        Stream<Integer> integerStream1 = Stream.of(11, 11, 25, 36, 25, 6);
        System.out.println(integerStream1.allMatch((a) -> {
            return a > 10;
        }));

        //any Match 流中是否有任意元素满足Predicate内置接口中的方法 返回Boolean
        Stream<Integer> integerStream2 = Stream.of(11, 11, 25, 36, 25, 6);
        System.out.println(integerStream2.anyMatch((a) -> {
            return a > 10;
        }));

        //none match 流中的元素是否都不满足Predicate内置接口中的方法 返回Boolean
        Stream<Integer> integerStream3 = Stream.of(11, 11, 25, 36, 25, 6);
        System.out.println(integerStream3.noneMatch((a) -> {
            return a > 0;
        }));

        //find first
        Stream<String> names = Stream.of("张三丰", "周芷若", "赵敏", "老顽童", "张无忌", "张狗");
        System.out.println(names.findFirst().get());

        //find any 效率更高 默认取第一个元素 只是同一个流的多次操作可能结果不同
        Stream<String> names1 = Stream.of("张三丰", "周芷若", "赵敏", "老顽童", "张无忌", "张狗");
        System.out.println(names1.filter((a) -> {
            return a.startsWith("张");
        }).findAny().get());

        //max方法 获取排序后的最后一个值
        System.out.println(Stream.of(999, 7, 6, 71, 2).max((o1, o2) -> {
            return o1 - o2;
        }).get());

        //min方法 获取排序后的第一个值
        System.out.println(Stream.of(999, 7, 6, 71, 2).min((o1, o2) -> {
            return o1 - o2;
        }).get());

        //reduce 参数: 1.默认值 2.对数据的处理方式
        System.out.println(Stream.of(999, 7, 6, 71, 2).reduce(10, Integer::sum)); //默认值是10 求和
        System.out.println(Stream.of("张三丰", "周芷若", "赵敏", "老顽童", "张无忌", "张狗").reduce("Start", String::concat)); //默认值Start 将字符串拼接

        //场景： 在三个数值型数组中找到元素和最大的那个数组
        ArrayList<Integer> arr1 = new ArrayList<>();
        Collections.addAll(arr1, 7, 6);
        ArrayList<Integer> arr2 = new ArrayList<>();
        Collections.addAll(arr2, 5, 7);
        ArrayList<Integer> arr3 = new ArrayList<>();
        Collections.addAll(arr3, 30, 6);
        Stream<ArrayList<Integer>> in = Stream.of(arr1, arr2, arr3);
        System.out.println(in.reduce(new ArrayList<Integer>() , (a, b) -> {
            AtomicInteger sumA = new AtomicInteger();
            AtomicInteger sumB = new AtomicInteger();
            a.forEach(sumA::addAndGet);
            b.forEach(sumB::addAndGet);
            return sumA.get() > sumB.get() ? a : b;
        }));

        Stream<Integer> integerStream4 = Stream.of(1, 2, 3, 9, 5);
        IntStream intStream = integerStream4.mapToInt(Integer::intValue); // 转化为了intStream 可以减少内存占用 减少自动装箱

        //concat 合并两个Stream流 流合并之后就会关闭 不可以继续操作合并前的两个流了
        Stream<String> liu1 = Stream.of("liu一");
        Stream<String> liu2 = Stream.of("liu二");
        Stream<String> concat = Stream.concat(liu1, liu2);
        concat.forEach(System.out::print);
    }
}
