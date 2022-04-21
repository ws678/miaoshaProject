package com.miaoshaProject.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author wangshuo
 * @Date 2022/4/16, 14:06
 * Please add a comment
 */
public class ValidationResult {

    //校验结果
    private boolean hasErrors = false;

    //存放错误信息的map
    private Map<String,String> errorMap = new HashMap<>();

    //实现通用的通过格式化字符串获取错误结果的方法
    public String getErrMsg(){

        //map --》 array --》 String
        String join = StringUtils.join(errorMap.values().toArray(), ",");
        return join;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }
}
