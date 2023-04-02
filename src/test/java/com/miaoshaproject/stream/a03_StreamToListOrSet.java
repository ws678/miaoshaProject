package com.miaoshaproject.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class a03_StreamToListOrSet {

    public static void main(String[] args) {

        //collect 收集方法 toList toSet
        Stream<String> aa = Stream.of("aa", "bb", "cc");
        List<String> collect = aa.collect(Collectors.toList());
        collect.forEach(System.out::println);
        Stream<String> bb = Stream.of("dd", "ee", "ff");
        bb.collect(Collectors.toSet()).forEach(System.out::println);

        //收集到指定的集合
        Stream<String> cc = Stream.of("a", "b", "c");
        cc.collect(Collectors.toCollection(ArrayList::new)).forEach(System.out::print);

        //收集到数组中
        Stream<String> x = Stream.of("x", "y", "z");
        Object[] objects = x.toArray(); //不写参数只可以转化为Object类型的数组
        Arrays.stream(objects).forEach(System.out::println);
        Stream<String> t = Stream.of("t", "y", "u");
        Arrays.stream(t.toArray(String[]::new)).forEach(System.out::println);

        //收集到现有集合
        ArrayList<String> list = new ArrayList<>();
        list.add("指定的业务集合");
        Stream<String> dd = Stream.of("a", "b", "c");
        dd.collect(Collectors.toCollection(() -> {
            return list;
        })).forEach(System.out::println);
    }
}
