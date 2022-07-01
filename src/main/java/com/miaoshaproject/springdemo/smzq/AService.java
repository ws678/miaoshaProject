package com.miaoshaproject.springdemo.smzq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 21:26
 * Please add a comment
 */
@Component
public class AService {

    @Autowired
    private BService bService;
    public void test(){
        System.out.println("AService");
        bService.test();
    }
}
