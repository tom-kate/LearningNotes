package com.tomkate.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 12:13
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/consul")
    public String PaymentConsul() {
        return "SpringCloud witch consul:" + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
