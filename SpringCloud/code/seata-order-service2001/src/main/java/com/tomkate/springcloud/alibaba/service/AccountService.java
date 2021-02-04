package com.tomkate.springcloud.alibaba.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 19:00
 */
@FeignClient(value = "seata-account-service")
public interface AccountService {
    /**
     * 修改账户余额
     *
     * @param userId
     * @param money
     */
    @PostMapping(value = "/account/decrease")
    void decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
