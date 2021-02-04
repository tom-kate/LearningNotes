package com.tomkate.springcloud.alibaba.service;

import com.tomkate.springcloud.alibaba.domain.Order;
import org.springframework.stereotype.Service;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 18:56
 */
public interface OrderService {
    void save(Order order);
}
