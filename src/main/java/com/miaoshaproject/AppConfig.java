package com.miaoshaproject;

import com.miaoshaproject.springdemo.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 10:24
 * Please add a comment
 */
//扫描需要自动注入的两个包
@ComponentScan("com.miaoshaproject.springdemo.smzq")
//打开切面代理
@EnableAspectJAutoProxy
//开启事务管理器
@EnableTransactionManagement
//开启事务
@Configuration
public class AppConfig {

    /*@Bean(name = "userService1")
    public UserService UserService1(){
        return null;
    }
    @Bean(name = "userService2")
    public UserService UserService2(){
        return new UserService();
    }*/
}
