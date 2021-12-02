package com.yuanshen.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenchenstart
 * @CreateTime: 2021/11/10 - 13:51
 * @Description:
 */

@Configuration
public class MybatisPlusConfig {
    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
