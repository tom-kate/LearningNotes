package com.tomkate.springcloud.controller;

import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import com.tomkate.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/5 17:59
 */
@RestController
@Slf4j
public class OrderController {

    /**
     * 服务提供者地址,单机
     */
    private static final String PAYMENT_URL = "http://localhost:8001";

    /**
     * 服务提供者地址,集群
     */
    private static final String PAYMENT_URLS = "http://cloud-payment-service";

    /**
     * 请求类
     */
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private LoadBalancer loadBalancer;

    @GetMapping(value = "consumer/payment/create")
    public CommomResult<Payment> create(Payment payment) {
        log.info("******consumer-create接口");
        return restTemplate.postForObject(PAYMENT_URLS + "/payment/create", payment, CommomResult.class);
    }

    @GetMapping(value = "consumer/payment/get/{id}")
    public CommomResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        log.info("******consumer-get接口");
        return restTemplate.getForObject(PAYMENT_URLS + "/payment/get/" + id, CommomResult.class);
    }

    @GetMapping(value = "consumer/payment/getEntity/{id}")
    public CommomResult<Payment> getPaymentById2(@PathVariable("id") Long id) {
        ResponseEntity<CommomResult> forEntity = restTemplate.getForEntity(PAYMENT_URLS + "/payment/get/" + id, CommomResult.class);
        if (forEntity.getStatusCode().is2xxSuccessful()) {
            log.info("statusCode:" + forEntity.getStatusCode() + "\t" + "statusHeads:" + forEntity.getHeaders());
            return forEntity.getBody();
        } else {
            return new CommomResult(500, "没有对应记录，查询ID：" + id, null);
        }
    }

    /**
     * CAS自旋锁实现轮询算法
     *
     * @return
     */
    @GetMapping(value = "consumer/payment/lb")
    public String paymentLB() {
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        if (instances == null || instances.size() < 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb", String.class);
    }

    @GetMapping(value = "consumer/getPaymentZipkinTest")
    public String getPaymentZipkinTest() {
        return restTemplate.getForObject(PAYMENT_URLS + "/payment/getZipkin", String.class);
    }

}
