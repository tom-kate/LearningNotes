package com.tomkate.springcloud.controller;

import cn.hutool.core.util.IdUtil;
import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import com.tomkate.springcloud.service.Impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/5 16:24
 */
@Slf4j
@RestController
public class PaymentController {
    @Resource
    private PaymentServiceImpl paymentServiceImpl;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommomResult create(@RequestBody Payment payment) {
        int result = paymentServiceImpl.create(payment);
        log.info("******插入结果为：" + result);
        if (result > 0) {
            return new CommomResult(200, "插入数据库成功,端口号:" + serverPort, result);
        } else {
            return new CommomResult(500, "插入数据库失败,端口号:" + serverPort, null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommomResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentServiceImpl.getPaymentById(id);
        log.info("******当前查询结果为：" + payment);
        if (null != payment) {
            return new CommomResult(200, "查询成功,端口号:" + serverPort, payment);
        } else {
            return new CommomResult(500, "没有对应记录，查询ID：" + id, null);
        }
    }

    @GetMapping(value = "payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("******element:" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance obj : instances) {
            log.info(obj.getServiceId() + "\t" + obj.getPort() + "\t" + obj.getHost() + "\t" + obj.getUri());
        }
        return discoveryClient;
    }

    @GetMapping(value = "payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping(value = "payment/feign/timeout")
    public String feignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping(value = "payment/getZipkin")
    public String getPaymentZipkin() {
        return "服务端 链路检测测试！！！";
    }
}
