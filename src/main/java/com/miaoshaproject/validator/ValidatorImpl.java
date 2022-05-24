package com.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author wangshuo
 * @Date 2022/4/16, 14:14
 * Please add a comment
 */
@Component//声明bean
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实现校验方法并返回校验结果
    public ValidationResult validator(Object bean){

        final ValidationResult result = new ValidationResult();
        final Set<ConstraintViolation<Object>> set = validator.validate(bean);
        if (set.size() > 0){
            result.setHasErrors(true);
            //Lambda表达式
            set.forEach(ConstraintViolation->{
                String errMsg = ConstraintViolation.getMessage();
                String propertyName = ConstraintViolation.getPropertyPath().toString();
                result.getErrorMap().put(propertyName,errMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
