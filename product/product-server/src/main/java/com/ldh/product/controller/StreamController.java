package com.ldh.product.controller;

import com.ldh.product.client.StreamClient;
import com.ldh.product.common.ProductInfoOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StreamController {
//    @Autowired
//    private StreamClient streamClient;
//
//    @RequestMapping("/send")
//    public void sendStream() {
//        streamClient.output().send(MessageBuilder.withPayload("halo stream messgae").build());
//    }
//
//    /**
//     * 发送对象消息
//     */
//    @RequestMapping("/sendobj")
//    public void sendStreamObj() {
//        ProductInfoOutput infoOutput=new ProductInfoOutput();
//        infoOutput.setProductId("888888");
//        infoOutput.setProductName("老白");
//        streamClient.output().send(MessageBuilder.withPayload(infoOutput).build());
//    }
}
