package com.miaoshaproject;

import com.miaoshaproject.demo.BeanDemo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Hello world!
 */
//@EnableAutoConfiguration//创建bean 开启整个工程基于spring boot 自动化的配置
@SpringBootApplication(scanBasePackages = {"com.miaoshaproject"})
//和EnableAutoConfiguration差不多
@MapperScan("com.miaoshaproject.dao")//扫描dao添加Mapper
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
