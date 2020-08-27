package com.ldh.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 接收端
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamResiver {
//    @StreamListener(StreamClient.INPUT)
//    public void process(Object msg) {
//        log.info("myStream message:{}", msg);
//    }
}
