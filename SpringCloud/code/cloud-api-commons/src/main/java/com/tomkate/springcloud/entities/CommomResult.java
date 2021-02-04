package com.tomkate.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/5 15:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommomResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommomResult(Integer code, String message) {
        this(code, message, null);
    }
}
