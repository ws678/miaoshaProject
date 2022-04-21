package com.miaoshaProject.service.impl;

import com.miaoshaProject.dao.OrderDOMapper;
import com.miaoshaProject.dao.SequenceDOMapper;
import com.miaoshaProject.dataobject.OrderDO;
import com.miaoshaProject.dataobject.SequenceDO;
import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.error.EnumBusinessError;
import com.miaoshaProject.service.ItemService;
import com.miaoshaProject.service.OrderService;
import com.miaoshaProject.service.UesrService;
import com.miaoshaProject.service.model.ItemModel;
import com.miaoshaProject.service.model.OrderModel;
import com.miaoshaProject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author wangshuo
 * @Date 2022/4/20, 9:35
 * Please add a comment
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    ItemService itemService;

    @Autowired
    UesrService uesrService;

    @Autowired
    SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {

        //校验下单状态 ： 下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        //校验用户信息
        UserModel byId = uesrService.getById(userId);
        if (byId == null)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        //校验库存信息
        if (amount <= 0 || amount > 99)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "订单数量不正确");
        //校验活动信息
        if (promoId != null) {
            //是否有对应活动商品
            if (promoId.intValue() != itemModel.getPromoModel().getId())
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息出错");
            //活动是否正在进行中
            if (itemModel.getPromoModel().getStatus() != 2)
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息出错");
        }
        //落单减库存
        boolean b = itemService.decreaseStock(itemId, amount);
        if (!b)
            throw new BusinessException(EnumBusinessError.STOCK_NOT_ENOUGH);
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setPromoId(promoId);
        orderModel.setAmount(amount);
        if (promoId != null)
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        else
            orderModel.setItemPrice(itemModel.getPrice());
        //相乘获得订单价格
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        //交易流水订单号
        orderModel.setId(generateOrderNO());
        //转化model
        OrderDO orderDO = convertFromOrderModel(orderModel);
        //新增订单
        orderDOMapper.insertSelective(orderDO);
        //增加销量
        itemService.increaseSales(orderModel);
        //返回前端
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)//不论外部事务成功与否 我这边都要提交事务，保证自增序列唯一性
    public String generateOrderNO() {
        //设计 ： 订单号有十六位，
        StringBuilder stringBuilder = new StringBuilder();
        // 前八位为时间信息
        LocalDateTime date = LocalDateTime.now();
        stringBuilder.append(date.format(DateTimeFormatter.ISO_DATE).replace("-", ""));
        // 中间六位为自增序列
        Integer sequence = 0;
        //1.获取当前sequence
        SequenceDO sequence_info = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequence_info.getCurrentValue();
        //2.sequence增加步长并入库
        sequence_info.setCurrentValue(sequence_info.getCurrentValue() + sequence_info.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequence_info);
        //3.拼接sequence
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {//当sequence被操作六位数以上后，中间自增序列就不止六位数了
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        // 最后两位为分库分表位   暂时写死为00
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {

        if (orderModel == null)
            return null;
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;
    }
}
