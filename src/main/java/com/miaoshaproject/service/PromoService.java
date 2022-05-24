package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoModel;

/**
 * @Author wangshuo
 * @Date 2022/4/21, 11:11
 * Please add a comment
 */
public interface PromoService {

    PromoModel getPromoByItemId(Integer itemId);
}
