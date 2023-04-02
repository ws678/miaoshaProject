package com.miaoshaproject.function;

import java.util.function.Consumer;

/*
    使用两个内置函数式接口进行业务逻辑编写
 */
public class ConsumerTestDemoTwo {

    public static void main(String[] args) {

        play((aa) -> {

            System.out.println(aa.toUpperCase());
        }, (aa) -> {

            System.out.println(aa.toLowerCase());
        });
    }

    public static void play(Consumer<String> consumer1, Consumer<String> consumer2) {

        String aa = "Hello World";
        /*consumer1.accept(aa);
        consumer2.accept(aa);*/

        //andYThen写法
        consumer1.andThen(consumer2).accept(aa);
    }
}
