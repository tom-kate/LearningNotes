package com.tomkate.springcloud.alibaba.service;

import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/12 16:11
 */
@Component
public class PaymentFallbackService implements PaymentService {
    @Override
    public CommomResult<Payment> paymentSQL(Long id) {
        return new CommomResult<Payment>(44444, "服务降级返回,---PaymentFallbackService", new Payment(id, "errorSerial"));
    }
}
