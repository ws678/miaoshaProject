package com.miaoshaProject.service.model;

import java.math.BigDecimal;

/**
 * @Author wangshuo
 * @Date 2022/4/20, 8:45
 * 用户下单的交易模型
 */
public class OrderModel {

    //String类型id
    private String id;

    private Integer userId;

    private Integer itemId;

    //若非空，表示该商品是秒杀商品
    private Integer promoId;

    //商品数量 若promoId非空则表示秒杀商品价格
    private Integer amount;

    //订单金额
    private BigDecimal orderPrice;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    //冗余一个字段  若promoId非空则表示秒杀订单价格
    private BigDecimal itemPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
}
