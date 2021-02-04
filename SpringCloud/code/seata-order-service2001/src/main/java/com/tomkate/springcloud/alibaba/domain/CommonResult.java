package com.tomkate.springcloud.alibaba.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/13 18:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String messages;
    private T data;

    public CommonResult(Integer code, String messages) {
        this(code, messages, null);
    }
}
