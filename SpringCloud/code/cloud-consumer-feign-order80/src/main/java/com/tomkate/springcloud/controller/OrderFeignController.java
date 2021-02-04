package com.tomkate.springcloud.controller;

import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import com.tomkate.springcloud.service.PaymentOpenFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 17:08
 */
@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentOpenFeignService paymentOpenFeignService;

    @GetMapping(value = "consumer/payment/get/{id}")
    public CommomResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentOpenFeignService.getPaymentById(id);
    }

    /**
     * 测试feign接口超时
     *
     * @return
     */
    @GetMapping(value = "consumer/payment/feign/timeout")
    public String feignTimeout() {
        return paymentOpenFeignService.feignTimeout();
    }
}
