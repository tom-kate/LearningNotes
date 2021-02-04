package com.tomkate.springcloud.service.Impl;

import com.tomkate.springcloud.dao.PaymentDao;
import com.tomkate.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/5 16:15
 */
@Service
public class PaymentServiceImpl {
    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
