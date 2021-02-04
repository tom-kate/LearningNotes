package com.tomkate.springcloud.alibaba.controller;

import com.tomkate.springcloud.alibaba.domain.CommonResult;
import com.tomkate.springcloud.alibaba.domain.Order;
import com.tomkate.springcloud.alibaba.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 19:12
 */
@RestController
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping(value = "order/create")
    public CommonResult create(Order order) {
        orderService.save(order);
        return new CommonResult(200, "订单完成");
    }
}
