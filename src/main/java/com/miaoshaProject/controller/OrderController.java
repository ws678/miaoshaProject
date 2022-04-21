package com.miaoshaProject.controller;

import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.error.EnumBusinessError;
import com.miaoshaProject.response.CommonReturnType;
import com.miaoshaProject.service.OrderService;
import com.miaoshaProject.service.model.OrderModel;
import com.miaoshaProject.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author wangshuo
 * @Date 2022/4/20, 14:17
 * Please add a comment
 */
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController extends BaseController {

    @Autowired
    OrderService orderService;

    //封装下单请求
    @RequestMapping("/create")
    @ResponseBody
    public CommonReturnType create(@RequestParam(name = "itemId") Integer itemId,
                                   @RequestParam(name = "amount") Integer amount,
                                   @RequestParam(name = "promoId",required = false) Integer promoId,
                                   HttpServletRequest request) throws BusinessException {

        //获取user信息
        /*Integer login_id = (Integer) request.getSession().getAttribute("LOGIN_USER");*/
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null) {
            return null;
        }
        String retValue = null;
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals("LOGIN_USER")) {

                retValue = cookieList[i].getValue();
                break;
            }
        }
        OrderModel orderModel = new OrderModel();
        if (retValue == null) {
            /*throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS,"用户未登录");*/
            orderModel = orderService.createOrder(17, itemId, promoId, amount);
        } else
            orderModel = orderService.createOrder(Integer.parseInt(retValue), itemId, promoId, amount);
        return CommonReturnType.create(orderModel);
    }
}
