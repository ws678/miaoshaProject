package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
    @Autowired
    RedisUtil redisUtil;

    //封装下单请求
    @RequestMapping("/create")
    @ResponseBody
    public CommonReturnType create(@RequestParam(name = "itemId") Integer itemId,
                                   @RequestParam(name = "amount") Integer amount,
                                   @RequestParam(name = "promoId",required = false) Integer promoId,
                                   HttpServletRequest request) throws BusinessException, UnknownHostException {

        //获取user信息
        /*Integer login_id = (Integer) request.getSession().getAttribute("LOGIN_USER");*/
        /*Cookie[] cookieList = request.getCookies();
        if (cookieList == null) {
            return null;
        }
        String retValue = null;
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals("LOGIN_USER")) {

                retValue = cookieList[i].getValue();
                break;
            }
        }*/
        InetAddress addr = InetAddress.getLocalHost();
        //2022-04-22修改session存入redis
        /*JedisPool jedisPool = JedisClientConfig.getJedisPool();
        Jedis jedis = JedisClientConfig.getJedis(jedisPool);

        String userId = jedis.hget(addr.getHostAddress(), "userId");
        JedisClientConfig.closeJedisAndJedisPool(jedisPool,jedis);*/

        //2022-06-07 改为使用redisTemplate获取userId
        String userId = (String) redisUtil.hget(addr.getHostAddress(), "userId");
        String userName = (String) redisUtil.hget(addr.getHostAddress(), "userName");
        /*OrderModel orderModel = new OrderModel();
        if (retValue == null) {
            *//*throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS,"用户未登录");*//*
            orderModel = orderService.createOrder(17, itemId, promoId, amount);
        } else
            orderModel = orderService.createOrder(Integer.parseInt(retValue), itemId, promoId, amount);*/
        OrderModel orderModel = orderService.createOrder(Integer.parseInt(userId), itemId, promoId, amount);
        return CommonReturnType.create(orderModel);
    }
}
