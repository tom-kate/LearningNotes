package com.tomkate.springcloud.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 19:53
 */
@Mapper
public interface StorageDao {
    /**
     * 扣减库存
     *
     * @param productId
     * @param count
     */
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
