package com.miaoshaproject.error;

/**
 * @Author wangshuo
 * @Date 2022/4/14, 8:39
 * Please add a comment
 */
public interface CommonError {

    public int getErrorCode();

    public String getErrorMsg();

    public CommonError setErrorMsg(String msg);
}
