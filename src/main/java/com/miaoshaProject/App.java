package com.miaoshaProject;

import com.miaoshaProject.dao.UserDOMapper;
import com.miaoshaProject.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
//@EnableAutoConfiguration//创建bean 开启整个工程基于spring boot 自动化的配置
@SpringBootApplication(scanBasePackages = {"com.miaoshaProject"})//和EnableAutoConfiguration差不多
@RestController//spring mvc
@MapperScan("com.miaoshaProject.dao")//扫描dao
public class App {

    //调试一下 先注入
    @Autowired
    private UserDOMapper userDOMapper;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class);
    }

    @RequestMapping("/test")//
    public String home(){

        //
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);
        if (userDO == null){
            return "用户对象不存在";
        }else {
            return userDO.getName();
        }
    }
}
