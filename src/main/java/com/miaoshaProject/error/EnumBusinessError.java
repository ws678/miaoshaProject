package com.miaoshaProject.error;

/**
 * @Author wangshuo
 * @Date 2022/4/14, 8:43
 * 自定义error
 */
public enum EnumBusinessError implements CommonError{
    //10001 参数不合法
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    //20000 未知错误
    UNKNOWN_ERROR(20000,"未知错误"),
    //200001 登录超时或未登录
    UN_LOGIN_ERROR(20001,"登录超时或未登录"),
    //以30000 开头的错误码代表用户信息错误
    USER_NOT_EXISTS(30001,"用户不存在"),
    REGISTER_OTP_ERROR(30002,"验证码错误"),
    REGISTER_REPEAT(30003,"该手机号已注册账户，请勿重复注册"),
    LOGIN_ERROR(30004,"用户手机号或密码不正确"),
    //以40000 开头的错误码代表交易错误
    STOCK_NOT_ENOUGH(40001,"库存不足")
    ;

    private EnumBusinessError(Integer code,String msg){

        this.errorCode = code;
        this.errorMsg = msg;
    }

    private int errorCode;
    private String errorMsg;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    //定制化的方法改动错误信息
    @Override
    public CommonError setErrorMsg(String msg) {
        this.errorMsg = msg;
        return this;
    }
}
