package com.tomkate.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/6 16:30
 */
@Component
public class Mylb implements LoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * CAS自旋锁实现
     *
     * @return
     */
    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!this.atomicInteger.compareAndSet(current, next));
        System.out.println("******第几次访问，次数next:" + next);
        return next;
    }

    /**
     * 负载均衡算法：rest接口第几次访问%服务器集群总数=实际调用服务器下标，每次重启后rest接口计数从1开始
     *
     * @param serviceInstance
     * @return
     */
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstance) {
        int index = getAndIncrement() % serviceInstance.size();
        return serviceInstance.get(index);
    }
}
