package com.miaoshaproject.error;

/**
 * @Author wangshuo
 * @Date 2022/4/14, 8:55
 * 包装器业务异常类实现
 */
public class BusinessException extends Exception implements CommonError{

    //强关联一个CommonError
    private CommonError commonError;

    //直接接受CommonError的传参用于构造业务异常
    public BusinessException(CommonError commonError){

        super();
        this.commonError = commonError;
    }

    //接收自定义异常传参用于构造自定义异常
    public BusinessException(CommonError commonError,String msg){

        super();
        this.commonError = commonError;
        this.commonError.setErrorMsg(msg);
    }

    @Override
    public int getErrorCode() {
        return commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String msg) {
        commonError.setErrorMsg(msg);
        return this;
    }
}
