package com.miaoshaproject.demo;

import com.miaoshaproject.springdemo.Order;
import com.miaoshaproject.springdemo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 10:26
 * Please add a comment
 */
//@Service
public class BeanDemo {

    @Autowired
    @Qualifier("qua")
    private UserService userService;

    /*public BeanDemo() {
        System.out.println("1");
    }*/

    //@Autowired
    //当只有一个有参的构造方法时，就会使用这个构造方法，参数会先 byType 再byName
    /*public BeanDemo(UserService userService) {
        System.out.println("2");
        this.userService1 = userService;
    }*/

    public void test(){
        System.out.println("test bean--------Hello World");
        Order defaultOrder = userService.getDefaultOrder();
        System.out.println(defaultOrder);
    }

    //开启spring事务管理方法内报错会全部回滚
    //设置事务的传播特性
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void a(){
        //请求数据库
    }
}
