package com.miaoshaproject.springdemo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author wangshuo
 * @Date 2022/6/18, 10:42
 * Please add a comment
 */
public class Order {

    private String orderId;
    private BigDecimal orderPrice;
    private Date createTime;

    public Order(String orderId, BigDecimal orderPrice, Date createTime) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderPrice=" + orderPrice +
                ", createTime=" + createTime +
                '}';
    }
}
