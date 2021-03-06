package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.OrderModel;

import java.util.List;

/**
 * @Author wangshuo
 * @Date 2022/4/19, 10:14
 * Please add a comment
 */
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException;

    void increaseSales(OrderModel orderModel) throws BusinessException;
}
