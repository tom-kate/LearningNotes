package com.tomkate.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tomkate.springcloud.alibaba.myhandler.CustomerBlockHandler;
import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/11 17:11
 */
@RestController
@Slf4j
public class RateLimitController {

    /**
     * 按资源名称限流
     *
     * @return
     */
    @GetMapping(value = "byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommomResult byResource() {
//        CommomResult commomResult = this.byUrl();
//        return commomResult;
        return new CommomResult(200, "按资源名称限流测试成功", new Payment(1L, "OK"));
    }

    /**
     * 兜底方法
     *
     * @param exception
     * @return
     */
    public CommomResult handleException(BlockException exception) {
        return new CommomResult(500, "兜底方法!!!!", new Payment(2L, "lose"));
    }

    /**
     * 按找URL限流
     *
     * @return
     */
    @GetMapping(value = "/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommomResult byUrl() {
        return new CommomResult(200, "按URL限流成功", new Payment(3L, "ok"));
    }

    @GetMapping(value = "/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException2")
    public CommomResult customerBlockHandler() {
        return new CommomResult(200, "按客戶自定义限流", new Payment(3L, "ok"));
    }
}
