package com.miaoshaproject.util.lock;

import java.math.BigDecimal;

public class A {

    int num = 0;
    private String name;
    private double price;
    private Integer count;
    private Long id;

    public long getNum() {
        return num;
    }

    public /*synchronized*/ void incr() {
        num++;
    }
}
