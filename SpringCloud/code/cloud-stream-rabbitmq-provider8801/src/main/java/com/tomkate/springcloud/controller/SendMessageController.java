package com.tomkate.springcloud.controller;

import com.tomkate.springcloud.service.impl.MessageProviderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/8 14:49
 */
@RestController
@Slf4j
public class SendMessageController {
    @Resource
    private MessageProviderImpl messageProviderImpl;

    @GetMapping(value = "/sendMessages")
    public String sendMessages() {
        return messageProviderImpl.sendMessages();
    }
}
