package com.miaoshaproject.function;

import java.util.function.Consumer;

/*
    使用Consumer接口将字符串全部转为小写
 */
public class ConsumerTestDemo {

    public static void main(String[] args) {

        toLowerCase((a) -> {

            String s = a.toLowerCase();
            System.out.println(s);
        });
    }

    public static void toLowerCase(Consumer<String> consumer) {

        String x = "FBIEFWBIFGW";
        consumer.accept(x);
    }
}
