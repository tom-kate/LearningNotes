package com.tomkate.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/5 18:01
 */
@Configuration
public class ApplicationContextConfig {
    /**
     * 获取RestTemplate对象（封装HTTPClint）
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
