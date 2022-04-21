package com.miaoshaProject.controller;

import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.error.EnumBusinessError;
import com.miaoshaProject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author wangshuo
 * @Date 2022/4/14, 11:15
 * 返回通用错误信息
 */
public class BaseController {

    public final static String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    //定义通用的exceptionHandler解决未被Controller吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest httpServletRequest, Exception e) {

        HashMap<Object, Object> hashMap = new HashMap<>();
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            hashMap.put("errCode", businessException.getErrorCode());
            hashMap.put("errMsg", businessException.getErrorMsg());
        } else {
            hashMap.put("errCode", EnumBusinessError.UNKNOWN_ERROR.getErrorCode());
            hashMap.put("errMsg", EnumBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        return CommonReturnType.create(hashMap, "fail");
    }
}
