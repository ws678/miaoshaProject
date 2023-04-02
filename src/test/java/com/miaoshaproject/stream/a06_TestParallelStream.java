package com.miaoshaproject.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class a06_TestParallelStream {

    private static final int a = 500000000;

    public static void main(String[] args) {

        //获取并行流的两种方法
        //1.直接创建
        ArrayList<String> strings = new ArrayList<>();
        Stream<String> stringStream = strings.parallelStream();

        //2.将串行的流转化为并行的流
        Stream<String> parallel = strings.stream().parallel();

        //使用for循环分别串行和并行循环5亿次进行自增 比较效率

        //不使用流
        int sum = 0;
        long startA = System.currentTimeMillis();
        for (int i = 0; i < a; i++) {
            sum += i;
        }
        long endA = System.currentTimeMillis();
        System.out.println("不使用流 " + (endA - startA));

        //stream串行
        long startB = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(0, a).reduce(0, Long::sum);
        long endB = System.currentTimeMillis();
        System.out.println("使用串行流 " + (endB - startB));

        //stream并行流
        long startC = System.currentTimeMillis();
        long reduce1 = LongStream.rangeClosed(0, a).parallel().reduce(0, Long::sum);
        long endC = System.currentTimeMillis();
        System.out.println("使用并行流 " + (endC - startC));

        //测试线程安全问题 将1000个元素的并行流装入线程不安全的ArrayList中 打印List大小 结果肯定是装不满的
        ArrayList<Integer> integers = new ArrayList<>(1000);
        IntStream.rangeClosed(0, 999).parallel().forEach(integers::add);
        System.out.println(integers.size());

        //解决问题方案
        /*
            一、使用Synchronize或者锁
            二、使用线程安全的集合
            三、使用Stream的collect toArray方法
         */

        //yi
        ArrayList<Integer> fangAnYi = new ArrayList<>(1000);
        IntStream.rangeClosed(0, 999).parallel().forEach((yi) -> {
            synchronized (fangAnYi) {
                fangAnYi.add(yi);
            }
        });
        System.out.println("fangAnYi:" + fangAnYi.size());

        //er
        Vector<Integer> fangAnEr = new Vector<Integer>(1000);
        IntStream.rangeClosed(0, 999).parallel().forEach(fangAnEr::add);
        System.out.println("fangAnEr:" + fangAnEr.size());

        //san
        List<Integer> fangAnSan = IntStream
                .rangeClosed(0, 999)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println("fangAnSan:" + fangAnSan.size());
    }
}
