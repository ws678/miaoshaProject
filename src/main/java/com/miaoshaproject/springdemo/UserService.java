package com.miaoshaproject.springdemo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 10:41
 * 声明为Spring Bean 实现InitializingBean接口
 */
@Component
@Qualifier("qua")
public class UserService implements InitializingBean {

    private Order defaultOrder;

    //属性设置后调用
    @Override
    public void afterPropertiesSet() throws Exception {
        // mysql --- defaultOrder
        defaultOrder = new Order("xxx", BigDecimal.ONE, new Date());
    }

    public Order getDefaultOrder() {
        return defaultOrder;
    }
}
