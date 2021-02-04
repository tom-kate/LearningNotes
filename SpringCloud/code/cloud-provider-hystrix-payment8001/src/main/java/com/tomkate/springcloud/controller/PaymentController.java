package com.tomkate.springcloud.controller;

import cn.hutool.core.util.IdUtil;
import com.tomkate.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 18:10
 */
@RestController
@Slf4j
public class PaymentController {

    @Value(value = "server.port")
    private String serverPort;
    @Resource
    private PaymentService paymentService;

    /**
     * 模拟请求成功
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("******result:" + result);
        return result;
    }

    /**
     * 模拟请求超时
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("******result:" + result);
        return result;
    }

    /**
     * 熔断测试
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/hystrix/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Long id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("******result:" + result);
        return result;
    }
}
