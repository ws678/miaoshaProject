package com.miaoshaproject.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class a05_TestGroupBy {

    public static void main(String[] args) {

        //根据学生年龄进行分组
        Stream<a04_TestAggS.Student> studentStream = Stream.of(
                new a04_TestAggS.Student("wangEr", 15, 99.00),
                new a04_TestAggS.Student("liSi", 55, 9.50),
                new a04_TestAggS.Student("liuWu", 5, 39.00),
                new a04_TestAggS.Student("zhaoLiu", 85, 9.00),
                new a04_TestAggS.Student("sunQi", 85, 4.00)
        );
        Map<Integer, List<a04_TestAggS.Student>> collect = studentStream.collect(Collectors.groupingBy(a04_TestAggS.Student::getAge));
        collect.forEach((k, v) -> {
            System.out.println("k:" + k + " v:" + v);
        });

        //将年龄大于等于18的分为一组 小于18的分为一组
        Stream<a04_TestAggS.Student> studentStream1 = Stream.of(
                new a04_TestAggS.Student("wangEr", 15, 99.00),
                new a04_TestAggS.Student("liSi", 55, 9.50),
                new a04_TestAggS.Student("liuWu", 5, 39.00),
                new a04_TestAggS.Student("zhaoLiu", 85, 9.00),
                new a04_TestAggS.Student("sunQi", 85, 4.00)
        );
        Map<String, List<a04_TestAggS.Student>> collect1 = studentStream1.collect(Collectors.groupingBy(student -> {
            if (student.getAge() >= 18) {
                return "已成年";
            } else {
                return "未成年";
            }
        }));
        collect1.forEach((k, v) -> {
            System.out.println("k:" + k + " v:" + v);
        });

        //先根据是否成年分组 再进行成绩是否及格分组
        Stream<a04_TestAggS.Student> studentStream2 = Stream.of(
                new a04_TestAggS.Student("wangEr", 15, 99.00),
                new a04_TestAggS.Student("liSi", 55, 9.50),
                new a04_TestAggS.Student("liuWu", 5, 39.00),
                new a04_TestAggS.Student("zhaoLiu", 85, 9.00),
                new a04_TestAggS.Student("sunQi", 85, 4.00)
        );
        Map<String, Map<String, List<a04_TestAggS.Student>>> collect2 = studentStream2.collect(Collectors.groupingBy((v) -> {
            return v.getAge() >= 18 ? "已成年" : "未成年";
        }, Collectors.groupingBy((v1) -> {
            return v1.getScore() >= 60.00 ? "已及格" : "未及格";
        }))); //分组了两次 会获得一个双重Map
        collect2.forEach((k, v) -> {
            System.out.println("外层：" + k + "\n" + v);
            v.forEach(((key, val) -> {
                System.out.println("内层：" + key + "\n" + val);
            }));
            System.out.println("-----------------------------");
        });

        //对流中的数据进行分区 partitioningBy 符合条件的分为True区 不符合则分置False
        Stream<a04_TestAggS.Student> studentStream3 = Stream.of(
                new a04_TestAggS.Student("wangEr", 15, 99.00),
                new a04_TestAggS.Student("liSi", 55, 9.50),
                new a04_TestAggS.Student("liuWu", 5, 39.00),
                new a04_TestAggS.Student("zhaoLiu", 85, 9.00),
                new a04_TestAggS.Student("sunQi", 85, 4.00)
        );
        Map<Boolean, List<a04_TestAggS.Student>> collect3 = studentStream3.collect(Collectors.partitioningBy((v) -> {
            return v.getScore() >= 60.00;
        }));
        collect3.forEach((k, v) -> {
            System.out.println("k:" + k + "\n v:" + v);
        });

        //对流中的数据进行拼接
        Stream<a04_TestAggS.Student> studentStream4 = Stream.of(
                new a04_TestAggS.Student("wangEr", 15, 99.00),
                new a04_TestAggS.Student("liSi", 55, 9.50),
                new a04_TestAggS.Student("liuWu", 5, 39.00),
                new a04_TestAggS.Student("zhaoLiu", 85, 9.00),
                new a04_TestAggS.Student("sunQi", 85, 4.00)
        );
        String collect4 = studentStream4.map(a04_TestAggS.Student::getName).collect(Collectors.joining(" -- ")); //将name进行拼接 分隔符为--
        System.out.println(collect4);
        Stream<String> strA = Stream.of("a", "b", "c");
        System.out.println(strA.collect(Collectors.joining(",", "[", "]"))); //加上其余的两个参数 分别是前缀与后缀
    }
}
