package com.tomkate.springcloud.alibaba.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 19:00
 */
@FeignClient(value = "seata-storage-service")
public interface StorageService {
    /**
     * 修改库存
     *
     * @param productId
     * @param count
     */
    @PostMapping(value = "/storage/decrease")
    void decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
