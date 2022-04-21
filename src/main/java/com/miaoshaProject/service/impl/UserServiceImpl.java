package com.miaoshaProject.service.impl;

import com.miaoshaProject.dao.UserDOMapper;
import com.miaoshaProject.dao.UserPasswordDOMapper;
import com.miaoshaProject.dataobject.UserDO;
import com.miaoshaProject.dataobject.UserPasswordDO;
import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.error.EnumBusinessError;
import com.miaoshaProject.service.UesrService;
import com.miaoshaProject.service.model.UserModel;
import com.miaoshaProject.validator.ValidationResult;
import com.miaoshaProject.validator.ValidatorImpl;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author wangshuo
 * @Date 2022/4/12, 18:45
 * Please add a comment
 */
@Service
public class UserServiceImpl implements UesrService {

    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    ValidatorImpl validator;

    @Override
    public UserModel getById(Integer id) {

        //调用mapper获取到对应的UserDO
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return null;
        }
        //通过用户id获取用户的加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);
        return convertFromDataObject(userDO, userPasswordDO);
    }

    @Override
    @Transactional//保证事务唯一性
    public void register(UserModel userModel) throws BusinessException {

        /*if (userModel == null)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        if (StringUtils.isEmpty(userModel.getName()) || userModel.getGender() == null ||
                StringUtils.isEmpty(userModel.getTelpphone()) || userModel.getAge() == null ||
                StringUtils.isEmpty(userModel.getRegisterMode())) {

            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/
        //更新校验规则
        ValidationResult result = this.validator.validator(userModel);
        if (result.isHasErrors())
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        UserDO userDO = convertFromModelObject(userModel);
        //使用selective
        //处理用户重复注册异常
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EnumBusinessError.REGISTER_REPEAT);
        }

        //把刚刚新增成功的userId赋值给UserPasswordDO用于密码表的新增
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModelObject(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {

        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if (userDO == null)
            throw new BusinessException(EnumBusinessError.LOGIN_ERROR);
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
        //比对用户信息内加密的密码是否与传输来的密码相匹配
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {

            throw new BusinessException(EnumBusinessError.LOGIN_ERROR);
        }
        //登录成功，返回model
        return userModel;
    }

    private UserPasswordDO convertPasswordFromModelObject(UserModel userModel) {

        if (userModel == null)
            return null;
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setUserId(userModel.getId());
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        return userPasswordDO;
    }

    private UserDO convertFromModelObject(UserModel userModel) {

        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    //不可以将service直接暴露给Controller，所以写方法返回UserModel
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {

        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        //把userDO的对应的bean属性copy到UserModel中
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO != null) {
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
