package com.miaoshaproject.repeat;

import org.junit.Test;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

//3. 配置重复注解
@myTest("ta")
@myTest("tb")
@myTest("tc")
public class testRepeat {

    @Test
    @myTest("mb")
    @myTest("mc")
    public void test() {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        //4. 解析重复注解 getAnnotationsByType() 用于获取重复的注解
        myTest[] annotationsByType = testRepeat.class.getAnnotationsByType(myTest.class);  //获取类上的重复注解
        Arrays.stream(annotationsByType).forEach(System.out::println);
        System.out.println("------------");
        myTest[] tests = testRepeat.class.getMethod("test").getAnnotationsByType(myTest.class); //获取方法上的重复注解
        Arrays.stream(tests).forEach(System.out::println);
    }
}

//1. 定义重复注解容器
@Retention(RetentionPolicy.RUNTIME)
@interface myClassA {
    myTest[] value();
}

//2. 创建重复注解
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(myClassA.class)
@interface myTest {
    String value();
}
