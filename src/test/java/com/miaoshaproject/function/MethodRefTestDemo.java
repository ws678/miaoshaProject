package com.miaoshaproject.function;

import io.netty.handler.codec.socksx.v4.AbstractSocks4Message;
import org.junit.Test;

import java.sql.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/*
    Lambda表达式进行方法引用
 */
public class MethodRefTestDemo {

    //已有的方法 数值型数组求和
    public static void getArrSum(int[] arr) {
        int sum = Arrays.stream(arr).sum();
        System.out.println(sum);
    }

    public static void main(String[] args) {

        //不使用方法引用
        printSum((a) -> {
            int sum = Arrays.stream(a).sum();
            System.out.println(sum);
        });

        //使用方法引用
        printSum(MethodRefTestDemo::getArrSum);
    }

    public static void printSum(Consumer<int[]> consumer) {
        int[] a = {7,6,4,8,2};
        consumer.accept(a);
    }

    //方法引用 对象名::方法名
    @Test
    public void test01() {

        Date date = new Date();
        Supplier<Long> supplier = date::getTime;
        System.out.println(supplier.get());
    }

    //调用类的构造器
    @Test
    public void test02() {

        //无参构造
        Supplier<TestPersonClass> supplier = TestPersonClass::new;
        TestPersonClass testPersonClass = supplier.get();
        System.out.println(testPersonClass);
        //有参构造
        BiFunction<String, Integer, TestPersonClass> biFunction = TestPersonClass::new;
        TestPersonClass person = biFunction.apply("哈哈哈", 99);
        System.out.println(person);
    }

    //调用数组的构造器
    @Test
    public void test03() {
        /*Function<Integer, int[]> function = (Integer len) -> {

            return new int[len];
        };*/
        Function<Integer, int[]> function = int[]::new;
        int[] apply = function.apply(10);
        System.out.println(apply.length);
    }
}
