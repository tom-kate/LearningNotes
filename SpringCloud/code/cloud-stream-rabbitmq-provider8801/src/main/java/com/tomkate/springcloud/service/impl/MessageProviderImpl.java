package com.tomkate.springcloud.service.impl;

import com.tomkate.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/8 14:42
 */
@Slf4j
@EnableBinding(Source.class)
public class MessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;

    @Override
    public String sendMessages() {
        String uuid = UUID.randomUUID().toString();
        boolean send = output.send(MessageBuilder.withPayload(uuid).build());
        log.info("******当前UUId：" + uuid);
        if (send) {
            return "发送成功";
        } else {
            return "发送失败";
        }
    }
}
