package com.tomkate.springcloud.alibaba.dao;

import com.tomkate.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 18:34
 */
@Mapper
public interface OrderDao {
    /**
     * 订单新增
     *
     * @param order
     */
    void save(Order order);

    /**
     * 根据订单ID修改订单状态
     *
     * @param userId
     * @param status
     */
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
