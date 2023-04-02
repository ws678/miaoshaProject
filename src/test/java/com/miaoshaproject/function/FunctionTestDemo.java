package com.miaoshaproject.function;

import java.util.function.Function;

/*
    使用Function方法将字符串类型的数字转化为Integer格式 然后将数字乘以五
 */
public class FunctionTestDemo {

    public static void main(String[] args) {

        //方法引用
        getNumber(Integer::parseInt , (aa) -> {

            return aa * 5;
        });
    }

    public static void getNumber(Function<String, Integer> function1, Function<Integer, Integer> function2) {

        String aa = "99";
        Integer apply = function1.andThen(function2).apply(aa);
        System.out.println(apply);
    }
}