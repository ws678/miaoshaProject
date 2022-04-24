package com.miaoshaProject.login_config;

import com.miaoshaProject.login_config.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description WebMvcConfigurerAdapter:扩展mvc
 * @Author rongtao
 * @Data 2019/4/24 13:41
 * https://www.codeleading.com/article/4517856247/
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册登录超时拦截器，并排除拦截登录请求
        registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/user/*");//update
        super.addInterceptors(registry);
    }
}