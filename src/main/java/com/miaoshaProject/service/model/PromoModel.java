package com.miaoshaProject.service.model;

import org.joda.time.DateTime;

import javax.xml.crypto.Data;
import java.math.BigDecimal;

/**
 * @Author wangshuo
 * @Date 2022/4/21, 8:49
 * 营销（秒杀模型）
 */
public class PromoModel {

    private Integer id;

    private String promoName;

    private DateTime startTime;

    private DateTime endTime;

    private Integer itemId;

    //秒杀活动状态 1.还未开始 2.进行中 3.已结束
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    //秒杀活动的价格
    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }
}
