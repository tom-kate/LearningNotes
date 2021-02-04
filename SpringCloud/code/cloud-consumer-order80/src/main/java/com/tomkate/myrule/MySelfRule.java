package com.tomkate.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 14:45
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        //定义为随机访问
        return new RandomRule();
    }
}
