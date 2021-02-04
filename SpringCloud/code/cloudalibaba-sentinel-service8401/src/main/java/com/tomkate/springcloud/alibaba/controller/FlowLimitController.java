package com.tomkate.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/11 12:33
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "get/A")
    public String getA() {
//        log.info("A方法");
//        try {
//            Thread.currentThread().sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "******A*******";
    }

    @GetMapping(value = "get/B")
    public String getB() {
        log.info(Thread.currentThread().getName() + "\t" + "*******testB");
        return "******B*******";
    }

    /**
     * 熔断测试-RT（平均处理时间）
     *
     * @return
     */
    @GetMapping(value = "get/C")
    public String getC() {
        //熔断测试-RT（平均处理时间）
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("getC 测试RT");
        return "******C*******";
    }

    /**
     * 熔断测试-异常比例
     *
     * @return
     */
    @GetMapping(value = "get/D")
    public String getD() {
        //熔断测试-异常比例
        int a = 10 / 0;
        log.info("测试 异常比例");
        return "******D*******";
    }

    /**
     * 熔断测试-异常数
     *
     * @return
     */
    @GetMapping(value = "get/E")
    public String getE() {
        //熔断测试-异常数
        int a = 10 / 0;
        log.info("测试 异常数");
        return "******D*******";
    }

    @GetMapping(value = "/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String getHotKey(@RequestParam(value = "p1", required = false) String p1,
                            @RequestParam(value = "p2", required = false) String p2) {
//        int i = 10 /0;  //SentinelResource 只管sentinel配置，java运行时报错不管
        return "******hotKey";
    }


    public String deal_testHotKey(String p1, String p2, BlockException exception) {
        return "******deal_hotKey,兜底方法！！！！";
    }
}
