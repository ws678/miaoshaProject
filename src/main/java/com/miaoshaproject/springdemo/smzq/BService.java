package com.miaoshaproject.springdemo.smzq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 21:27
 * Please add a comment
 */
@Component
public class BService {

    @Autowired
    private AService aService;
    public void test(){
        System.out.println("BService");
    }
}
