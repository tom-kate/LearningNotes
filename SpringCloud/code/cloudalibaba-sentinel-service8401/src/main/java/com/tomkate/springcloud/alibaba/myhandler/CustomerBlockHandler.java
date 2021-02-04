package com.tomkate.springcloud.alibaba.myhandler;


import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.tomkate.springcloud.entities.CommomResult;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/11 17:33
 */

/**
 * Sentinel自定义兜底方法
 */
public class CustomerBlockHandler {
    public static CommomResult handlerException1(BlockException exception) {
        return new CommomResult(55555, "按客戶自定义,global handlerException----1");
    }

    public static CommomResult handlerException2(BlockException exception) {
        return new CommomResult(55555, "按客戶自定义,global handlerException----2");
    }
}
