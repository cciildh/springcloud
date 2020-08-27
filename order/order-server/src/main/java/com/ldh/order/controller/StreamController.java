package com.ldh.order.controller;

import com.ldh.order.dto.OrderDTO;
import com.ldh.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {
    @Autowired
    private StreamClient streamClient;

    @RequestMapping("/send")
    public void sendStream() {
        streamClient.output().send(MessageBuilder.withPayload("halo stream messgae").build());
    }

    /**
     * 发送对象消息
     */
    @RequestMapping("/sendobj")
    public void sendStreamObj() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setOrderId("66666");
        orderDTO.setBuyerName("果果");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
