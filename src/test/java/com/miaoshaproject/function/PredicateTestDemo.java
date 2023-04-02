package com.miaoshaproject.function;

import java.util.function.Predicate;

/*
    使用Predicate接口判断一个人的名字长度是否超过三个字 是否超过五个字
 */
public class PredicateTestDemo {

    public static void main(String[] args) {

        isLongName((name) -> {

            return name.length() > 3;
        }, (name) -> {

            return name.length() < 5;
        });
    }

    public static void isLongName(Predicate<String> predicate1, Predicate<String> predicate2) {

        String name = "娜扎";
        //and
        boolean test = predicate1.and(predicate2).test(name);
        //or
        boolean test1 = predicate1.or(predicate2).test(name);
        //negate 取反
        boolean test2 = predicate1.negate().test(name);
        System.out.println(test + "\n" + test1 + "\n" + test2);
    }
}
