package com.tomkate.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 15:58
 */
public interface LoadBalancer {
    ServiceInstance instances(List<ServiceInstance> serviceInstance);
}
