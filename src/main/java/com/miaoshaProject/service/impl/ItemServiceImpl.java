package com.miaoshaProject.service.impl;

import com.miaoshaProject.dao.ItemDOMapper;
import com.miaoshaProject.dao.ItemStockDOMapper;
import com.miaoshaProject.dataobject.ItemDO;
import com.miaoshaProject.dataobject.ItemStockDO;
import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.error.EnumBusinessError;
import com.miaoshaProject.service.ItemService;
import com.miaoshaProject.service.PromoService;
import com.miaoshaProject.service.model.ItemModel;
import com.miaoshaProject.service.model.OrderModel;
import com.miaoshaProject.service.model.PromoModel;
import com.miaoshaProject.validator.ValidationResult;
import com.miaoshaProject.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wangshuo
 * @Date 2022/4/19, 10:17
 * Please add a comment
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ValidatorImpl validator;
    @Autowired
    ItemDOMapper itemDOMapper;
    @Autowired
    ItemStockDOMapper itemStockDOMapper;
    @Autowired
    PromoService promoService;

    @Override
    @Transactional//保证事务唯一性
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {

        //校验入参
        ValidationResult validator = this.validator.validator(itemModel);
        if (validator.isHasErrors())
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, validator.getErrMsg());
        //转化model -》 dataObject
        ItemDO itemDO = convertItemDOFromItemModel(itemModel);
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //返回创建完成的对象
        return this.getItemById(itemDO.getId());
    }

    @Override
    public List<ItemModel> listItem() {

        List<ItemDO> list = itemDOMapper.listItem();
        //java8 stream  将itemDO map成为 itemModel
        List<ItemModel> modelList = list.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return modelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {

        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null)
            return null;
        //操作获得库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);
        //convert
        ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);

        //获取活动商品信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if (promoModel != null && promoModel.getStatus() != 3)
            itemModel.setPromoModel(promoModel);
        return itemModel;
    }

    //减库存
    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {

        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        int affectedRow = itemStockDOMapper.decreaseStock(orderModel);
        //影响条数大于0即为成功
        return affectedRow > 0;
    }

    //加销量
    @Override
    @Transactional
    public void increaseSales(OrderModel orderModel) throws BusinessException {
        itemDOMapper.increaseSales(orderModel);
    }

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {

        if (itemModel == null)
            return null;
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {

        if (itemModel == null)
            return null;
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {

        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
