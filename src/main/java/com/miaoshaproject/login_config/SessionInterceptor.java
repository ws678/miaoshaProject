package com.miaoshaproject.login_config;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EnumBusinessError;
import com.miaoshaproject.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Description 登录超时拦截器
 * @Author rongtao
 * @Data 2019/4/24 13:37
 * https://www.codeleading.com/article/4517856247/
 */
@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {

    //注入template
    private static RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //拦截action
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /*UserDO user = (UserDO) request.getSession().getAttribute("user");
        System.out.println(user);*/

        //2022-04-22修改session存入redis
        InetAddress addr = InetAddress.getLocalHost();
        /*JedisPool jedisPool = JedisClientConfig.getJedisPool();
        Jedis jedis = JedisClientConfig.getJedis(jedisPool);
        String userId = jedis.hget(addr.getHostAddress(), "userId");
        JedisClientConfig.closeJedisAndJedisPool(jedisPool,jedis);*/

        //2022-05-15 使用redisTemplate
        Object userId = redisTemplate.opsForHash().get(addr.getHostAddress(), "userId");
        //session中User过期
        if (userId == null) {
            String uri = request.getRequestURI();
            System.out.println(uri);
            //ajax请求响应头会有，x-requested-with
            if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with")
                    .equalsIgnoreCase("XMLHttpRequest")) {
                //在响应头设置session状态
                response.setHeader("sessionstatus", "timeout");
                response.setHeader("url", uri.substring(0, uri.indexOf("/", 1)));
            } else {
                /*PrintWriter out = response.getWriter();
                StringBuilder sb = new StringBuilder();
                sb.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
                sb.append("alert(\"登录超时，请重新登录\");");
                sb.append("window.top.location.href=\"");
                sb.append("/login.jsp");
                sb.append("\";</script>");
                out.print(sb.toString());
                out.close();*/
                throw new BusinessException(EnumBusinessError.UN_LOGIN_ERROR);
            }
            //返回false不再调用其他的拦截器和处理器
            return false;
        } else {
            //用户是登录状态 重新设置session过期时间
            /*JedisPool jedisPool_new = JedisClientConfig.getJedisPool();
            Jedis jedis_new = JedisClientConfig.getJedis(jedisPool_new);
            jedis_new.expire(addr.getHostAddress(),60 * 30);
            JedisClientConfig.closeJedisAndJedisPool(jedisPool_new,jedis_new);*/
            //2022-05-15 使用redisTemplate
            redisTemplate.expire(addr.getHostAddress(), 60 * 30, TimeUnit.SECONDS);
        }
        return true;
    }
}