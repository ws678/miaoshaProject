package com.miaoshaproject.stream;

import com.sun.tools.javac.util.List;

import java.util.stream.Stream;

public class a02_Demo2 {

    public static void main(String[] args) {

        List<String> one = List.of("迪丽热巴", "老子", "庄子", "洪七公", "苏星河", "张天爱", "孙子");
        List<String> two = List.of("古力娜扎", "张无忌", "张三丰", "赵丽颖", "张二狗", "张天爱", "张三");

        //1.第一个队伍只要名字长度为3个字的成员 并且只要前三个队员
        Stream<String> oneStream = one.stream().filter(a -> a.length() == 3).limit(3);
        //2.第二个队伍只要姓张的成员 并且不要前两个成员
        Stream<String> twoStream = two.stream().filter(a -> a.startsWith("张")).skip(2);
        //3.将两个队伍合并
        Stream<String> streamConcat = Stream.concat(oneStream, twoStream);
        //4.根据姓名创建Person对象 打印整个对象的信息
        streamConcat.map(Person2::new).forEach(System.out::println);
    }
}
