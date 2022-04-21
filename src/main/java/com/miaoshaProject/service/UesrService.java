package com.miaoshaProject.service;

import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.service.model.UserModel;

/**
 * @Author wangshuo
 * @Date 2022/4/12, 18:45
 * Please add a comment
 */
public interface UesrService {

    UserModel getById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
}
