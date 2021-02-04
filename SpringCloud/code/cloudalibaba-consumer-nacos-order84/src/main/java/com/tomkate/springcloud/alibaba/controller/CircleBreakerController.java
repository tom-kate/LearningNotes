package com.tomkate.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tomkate.springcloud.alibaba.service.PaymentService;
import com.tomkate.springcloud.entities.CommomResult;
import com.tomkate.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/12 15:08
 */
@RestController
@Slf4j
public class CircleBreakerController {

    public static final String SERVER_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback")//没有配置 返回错误页面
//    @SentinelResource(value = "fallback", fallback = "fallBackFunction")//fallback只负责处理业务异常
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback", fallback = "fallBackFunction", blockHandler = "blockHandler", exceptionsToIgnore = {IllegalArgumentException.class})
    //blockHandler优先级高于fallbak  exceptionsToIgnore配置忽略该异常的fallback
    public CommomResult<Payment> fallback(@PathVariable("id") Long id) {
        CommomResult<Payment> result = restTemplate.getForObject(SERVER_URL + "/paymentSQL/" + id, CommomResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常....");
        } else if (null == result.getData()) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录,空指针异常");
        }
        return result;
    }

    /**
     * 本例是fallback方法
     *
     * @param id
     * @param e
     * @return
     */
    public CommomResult<Payment> fallBackFunction(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, "null");
        return new CommomResult<>(444, "兜底异常handlerFallback,exception内容  " + e.getMessage(), payment);
    }

    /**
     * 本例是blockHankler
     *
     * @param id
     * @param blockException
     * @return
     */
    public CommomResult blockHandler(@PathVariable Long id, BlockException blockException) {
        Payment payment = new Payment(id, "null");
        return new CommomResult<>(445, "blockHandler-sentinel限流,无此流水: blockException  " + blockException.getMessage(), payment);
    }

    /**
     * openFerign远程调用 服务异常统一兜底
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommomResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }
}
