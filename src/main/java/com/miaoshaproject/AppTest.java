package com.miaoshaproject;

import com.miaoshaproject.demo.BeanDemo;
import com.miaoshaproject.springdemo.Order;
import com.miaoshaproject.springdemo.smzq.AService;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 10:28
 * Please add a comment
 */
public class AppTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //BeanDemo bean = applicationContext.getBean("beanDemo", BeanDemo.class);
        //AService bean = applicationContext.getBean("AService", AService.class);
        //bean.test();
        //编程式定义Bean
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        //定义另外一个Bean的类型
        beanDefinition.setBeanClass(Order.class);
        //向容器注册（要注意这里是异步的）
        applicationContext.registerBeanDefinition("orderBean", beanDefinition);
        //在启动类不要刷新 GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once
        //applicationContext.refresh();
        //Order orderBean = applicationContext.getBean("orderBean", Order.class);
    }
}
