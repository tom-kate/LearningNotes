package com.tomkate.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 12:22
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * 负载均衡获取restTemplate对象
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
