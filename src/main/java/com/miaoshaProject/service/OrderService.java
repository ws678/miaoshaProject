package com.miaoshaProject.service;

import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.service.model.OrderModel;

/**
 * @Author wangshuo
 * @Date 2022/4/20, 9:35
 * Please add a comment
 */
public interface OrderService {

    //通过前端url传入秒杀活动Id，然后在下单接口内校验对应id是否属于对应商品且活动以开始
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
