package com.miaoshaproject.baomidou;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 配置类
 * @author: wangshuo
 * @date: 2023-04-01 16:18:52
 */
@Configuration
public class MybatisPlusConfig {

    //配置分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        return new PaginationInterceptor();
    }
}
