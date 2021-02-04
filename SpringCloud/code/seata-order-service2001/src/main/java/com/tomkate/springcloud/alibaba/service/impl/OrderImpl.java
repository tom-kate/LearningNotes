package com.tomkate.springcloud.alibaba.service.impl;

import com.tomkate.springcloud.alibaba.dao.OrderDao;
import com.tomkate.springcloud.alibaba.domain.Order;
import com.tomkate.springcloud.alibaba.service.AccountService;
import com.tomkate.springcloud.alibaba.service.OrderService;
import com.tomkate.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 18:57
 */
@Service
@Slf4j
public class OrderImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    /**
     * 创建订单->调用库存服务扣减库存->调用账户服务扣减账户余额->修改订单状态
     * 简单说：下订单->扣库存->减余额->改状态
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void save(Order order) {
        log.info("------>开始创建订单！");
        //1.创建订单
        orderDao.save(order);
        //2.修改库存
        log.info("----->订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存，做扣减end");
        //3.修改账户余额
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");
        //4.修改订单状态
        log.info("----->修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("----->修改订单状态结束");

        log.info("----->下订单结束了，O(∩_∩)O哈哈~");
    }
}
