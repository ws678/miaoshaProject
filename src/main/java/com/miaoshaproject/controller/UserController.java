package com.miaoshaproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EnumBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UesrService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.util.lock.RedisLock;
import com.miaoshaproject.util.redis.RedisUtil;
import jodd.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisLock redisLock;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String USER_LOCK = "lock_user_";
    private static final String LOGIN_FAIL_COUNT = "pw_fail_count_";

    //库存数量
    private static int num = 3;

    private HashMap<String, String> codeMap = new HashMap<>();

    @RequestMapping(value = "/toftl", method = {RequestMethod.GET})
    public String toFreemarker1(Map<String, Object> map) {

        map.put("name", "测试freemarker和controller返回");
        return "test_freemarker_1";
    }

    //测试redis实现分布式锁
    @RequestMapping(value = "/testredis")
    @ResponseBody
    public Map<String, String> testRedis() throws InterruptedException {
        HashMap<String, String> map = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName()).append(Thread.currentThread().getStackTrace()[1].getMethodName());
        String s = sb.toString().replace(".", "");
        redisLock.setKey(s);
        for (int i = 0; i < 5; i++) {

            new Thread(() -> {

                redisLock.lock();
                //调用减少库存的方法
                boolean b = reduceStock();
                redisLock.unlock();
                if (b)
                    map.put(Thread.currentThread().getName(), "减少库存成功");
                else
                    System.out.println(Thread.currentThread().getName() + "减少库存失败");
            }, "Thread" + i).start();
        }

        Thread.sleep(10000);
        return map;
    }

    //测试redis实现分布式锁
    @RequestMapping(value = "/testredis1")
    @ResponseBody
    public CommonReturnType testRedis1() {

        //22-07-06 登录锁定需求 author：王硕
        Object isLock = redisTemplate.opsForValue().get(USER_LOCK + "123");
        if (isLock != null)
            return CommonReturnType.create("密码输错次数过多，用户已被锁定！请一小时后重试！");
        //22-07-06 登录锁定需求 author：王硕
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer failCount = (Integer) valueOperations.get(LOGIN_FAIL_COUNT + "123");
        if (failCount == null)
            valueOperations.set(LOGIN_FAIL_COUNT + "123", 1, 300, TimeUnit.SECONDS);
        else if (failCount >= 5)
            valueOperations.set(USER_LOCK + "123", 1, 3600, TimeUnit.SECONDS);
        else if (failCount < 5) {
            redisTemplate.opsForValue().increment(LOGIN_FAIL_COUNT + "123");
        }
        System.out.println(failCount);
        return CommonReturnType.create("用户名或密码错误");
    }

    //测试手动解封
    @RequestMapping(value = "/testredis2/{userId}")
    @ResponseBody
    public CommonReturnType notSilent(@PathVariable("userId") Long userId) {
        //入参校验
        if (StringUtil.isBlank(userId.toString())) {
            return CommonReturnType.create("解锁失败！ 原因：用户为空");
        }
        Boolean del1 = redisTemplate.delete(USER_LOCK + userId);
        redisTemplate.delete(LOGIN_FAIL_COUNT + userId);
        if (del1)
            return CommonReturnType.create("解锁成功");
        else
            return CommonReturnType.create("解锁失败！ 原因：用户未处于锁定状态");
    }

    //减少库存的方法
    public boolean reduceStock() {
        if (num > 0) {
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            num--;
            return true;
        } else
            return false;
    }

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
        /*JedisPool jedisPool = JedisClientConfig.getJedisPool();
        Jedis jedis = JedisClientConfig.getJedis(jedisPool);*/
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userModel.getId().toString());
        userMap.put("userName", userModel.getName());
        userMap.put("userAge", userModel.getAge().toString());
        userMap.put("userGender", userModel.getGender().toString());
        userMap.put("userTelPhone", userModel.getTelpphone());
        //取IP地址作为key
        InetAddress addr = InetAddress.getLocalHost();
        /*jedis.hmset(addr.getHostAddress(), userMap);
        //设置三十分钟过期
        jedis.expire(addr.getHostAddress(), 60 * 30);
        //关闭连接对象
        JedisClientConfig.closeJedisAndJedisPool(jedisPool, jedis);*/

        //2022-05-15 使用redisTemplate
        redisUtil.hmset(addr.getHostAddress(), userMap, 60 * 30);
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
