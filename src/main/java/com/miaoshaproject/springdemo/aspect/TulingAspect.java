package com.miaoshaproject.springdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 11:24
 * Please add a comment
 */
@Aspect
@Component
public class TulingAspect {

    //切到test方法
    @Before("execution(public void com.miaoshaproject.demo.BeanDemo.test())")
    public void tulingBefore(){
        System.out.println("tuling Before");
    }
}
