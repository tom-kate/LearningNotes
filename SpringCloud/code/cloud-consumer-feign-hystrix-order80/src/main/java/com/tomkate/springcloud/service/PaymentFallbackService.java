package com.tomkate.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 21:37
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "------PaymentFallbackService fall back paymentInfo_OK 兜底方法";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "------PaymentFallbackService fall back paymentInfo_TimeOut 兜底方法";
    }
}
