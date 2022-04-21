package com.miaoshaProject.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author wangshuo
 * @Date 2022/4/12, 19:46
 * 要实现 Serializable接口，不然没办法放到session中
 */
public class UserModel {

    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotNull(message = "性别填写有误")
    private Integer gender;
    @NotNull(message = "年龄填写有误")
    @Min(value = 0, message = "年龄必须大于零")
    @Max(value = 150, message = "年龄不能大于150")
    private Integer age;
    @NotBlank(message = "手机号不能为空")
    private String telpphone;
    private String registerMode;
    private String thiredPartyId;
    @NotBlank(message = "密码不能为空")
    private String encrptPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelpphone() {
        return telpphone;
    }

    public void setTelpphone(String telpphone) {
        this.telpphone = telpphone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThiredPartyId() {
        return thiredPartyId;
    }

    public void setThiredPartyId(String thiredPartyId) {
        this.thiredPartyId = thiredPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }
}
