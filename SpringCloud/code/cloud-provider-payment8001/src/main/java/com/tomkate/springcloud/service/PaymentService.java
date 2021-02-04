package com.tomkate.springcloud.service;

import com.tomkate.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/5 16:15
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
