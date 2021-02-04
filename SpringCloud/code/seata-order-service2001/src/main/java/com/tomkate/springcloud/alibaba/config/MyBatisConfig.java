package com.tomkate.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 18:26
 */
@Configuration
@MapperScan({"com.tomkate.springcloud.alibaba.dao"})
public class MyBatisConfig {
}
