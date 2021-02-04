package com.tomkate.springcloud.alibaba.service;

import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/12 16:10
 */
@FeignClient(value = "nacos-payment-provider", fallback = PaymentFallbackService.class)
public interface PaymentService {
    @GetMapping(value = "/paymentSQL/{id}")
    public CommomResult<Payment> paymentSQL(@PathVariable("id") Long id);
}
