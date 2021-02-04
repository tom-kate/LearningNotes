package com.tomkate.springcloud.service;

import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 17:04
 */
@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentOpenFeignService {
    @GetMapping(value = "/payment/get/{id}")
    public CommomResult<Payment> getPaymentById(@PathVariable("id") Long id);

    /**
     * 测试feign超时
     *
     * @return
     */
    @GetMapping(value = "payment/feign/timeout")
    public String feignTimeout();
}
