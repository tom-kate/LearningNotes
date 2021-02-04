package com.tomkate.springcloud.alibaba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/11 9:56
 */
@RestController
@Slf4j
@RefreshScope
public class ConfigClientController {
    @Value(value = "${config.info}")
    private String NacosInfo;

    @GetMapping(value = "nacos/info")
    public String getNacosInfo() {
        return NacosInfo;
    }
}
