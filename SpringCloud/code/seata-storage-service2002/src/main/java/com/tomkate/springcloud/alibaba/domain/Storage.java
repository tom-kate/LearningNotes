package com.tomkate.springcloud.alibaba.domain;

import lombok.Data;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 19:41
 */
@Data
public class Storage {
    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 总库存
     */
    private Integer total;

    /**
     * 已用库存
     */
    private Integer used;

    /**
     * 剩余库存
     */
    private Integer residue;
}
