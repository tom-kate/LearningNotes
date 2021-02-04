package com.tomkate.springcloud.alibaba.service;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 19:54
 */
public interface StorageService {
    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);
}
