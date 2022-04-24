package com.miaoshaProject.controller;

import com.alibaba.druid.util.Base64;
import com.alibaba.druid.util.StringUtils;
import com.miaoshaProject.controller.viewobject.UserVO;
import com.miaoshaProject.error.BusinessException;
import com.miaoshaProject.error.EnumBusinessError;
import com.miaoshaProject.login_config.JedisClientConfig;
import com.miaoshaProject.response.CommonReturnType;
import com.miaoshaProject.service.UesrService;
import com.miaoshaProject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author wangshuo
 * @Date 2022/4/12, 18:42
 * Please add a comment
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UserController extends BaseController {

    @Autowired
    UesrService uesrService;

    private HashMap<String, String> codeMap = new HashMap<>();

    //用户注册接口
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone,
                                  @RequestParam(name = "encrptPassword") String encrptPassword,
                                  HttpServletRequest request, HttpServletResponse response) throws BusinessException, NoSuchAlgorithmException, UnknownHostException {

        //入参校验
        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(encrptPassword)) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务
        UserModel userModel = uesrService.validateLogin(telphone, this.encodeByMD5(encrptPassword));
        //服务层没throw Exception，用户登录成功，将凭证加入session内
        /*request.getSession().setAttribute("LOGIN_USER", userModel.getId());*/
        //2022-04-24将session存入redis
        manageSession(userModel);
        return CommonReturnType.create(userModel);
    }

    private void manageSession(UserModel userModel) throws UnknownHostException {
        JedisClientConfig jedisClientConfig = new JedisClientConfig();
        JedisPool jedisPool = jedisClientConfig.getJedisPool();
        Jedis jedis = jedisClientConfig.getJedis(jedisPool);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId", String.valueOf(userModel.getId()));
        userMap.put("userName", userModel.getName());
        userMap.put("userAge", String.valueOf(userModel.getAge()));
        userMap.put("userGender", String.valueOf(userModel.getGender()));
        userMap.put("userTelPhone", userModel.getTelpphone());
        //取IP地址作为key
        InetAddress addr = InetAddress.getLocalHost();
        jedis.hmset(addr.getHostAddress(), userMap);
        //设置三十分钟过期
        jedis.expire("login_user", 60 * 30);
        //关闭连接对象
        jedisClientConfig.closeJedisAndJedisPool(jedisPool, jedis);
    }

    //用户注册接口
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType userRegister(@RequestParam(name = "telphone") String telphone,
                                         @RequestParam(name = "otpCode") String otpCode,
                                         @RequestParam(name = "name") String name,
                                         @RequestParam(name = "gender") String gender,
                                         @RequestParam(name = "age") Integer age,
                                         @RequestParam(name = "encrptPassword") String encrptPassword) throws BusinessException, NoSuchAlgorithmException {

        //验证手机号和对应的optCode是否符合
        /*String obj = (String)this.httpServletRequest.getSession().getAttribute(telphone);*/
        String s = codeMap.get(telphone);

        //用druid类库的equals方法，这个有判空处理
        /*if (!StringUtils.equals(obj, otpCode))
            throw new BusinessException(EnumBusinessError.REGISTER_OTP_ERROR);*/
        if (!StringUtils.equals(s, otpCode))
            throw new BusinessException(EnumBusinessError.REGISTER_OTP_ERROR);
        //用户的注册流程 不加构造方法了先，看的清楚点
        UserModel userModel = new UserModel();
        userModel.setAge(age);
        if (StringUtils.equals(gender, "男")) {
            userModel.setGender(1);
        } else {
            userModel.setGender(2);
        }
        userModel.setName(name);
        userModel.setRegisterMode("byphone");
        userModel.setTelpphone(telphone);
        //md5加密密码
        userModel.setEncrptPassword(this.encodeByMD5(encrptPassword));
        uesrService.register(userModel);
        return CommonReturnType.create("注册成功，请重新登录");
    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {

        //需要按照一定的规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(89999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //将opt验证码同用户的手机号码关联  使用httpSession的方式绑定他的手机号与optCode
        /*this.httpServletRequest.getSession().setAttribute(telphone, otpCode);*/
        //试试存map 不行就存redis
        codeMap.put(telphone, otpCode);
        System.out.println(telphone + " / " + otpCode);
        //通过短信通道将验证码发送给用户（省略）
        return CommonReturnType.create(otpCode);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {

        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = uesrService.getById(id);

        if (userModel == null) {
            //返回用户不存在异常
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
            //空指针异常
            // userModel.setAge(1514);
        }
        //将核心领域模型对象转化为可供UI使用的viewObject
        //返回通用对象
        return CommonReturnType.create(convertFromModel(userModel));
    }

    //MD5转码
    public String encodeByMD5(String str) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newString = base64Encoder.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
        return newString;
    }

    //BeanCopy
    private UserVO convertFromModel(UserModel userModel) {

        if (userModel == null)
            return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
