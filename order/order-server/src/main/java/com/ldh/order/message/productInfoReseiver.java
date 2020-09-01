package com.ldh.order.message;

import com.ldh.order.utlis.JsonUtils;
import com.ldh.product.common.ProductInfoOutput;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class productInfoReseiver {
    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "productinfo", autoDelete = "true"),
            exchange = @Exchange(value = "order"),
            key = "productinfokey"
    ))
    public void process(String msg) {

        List<ProductInfoOutput> productInfoOutputList = JsonUtils.jsonToList(msg, ProductInfoOutput.class);

        for (ProductInfoOutput infoOutput : productInfoOutputList) {
            System.out.println("接收到的消息是:  " + infoOutput);
            //把库存数量存入到redis中
            redisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE, infoOutput.getProductId()),
                    String.valueOf(infoOutput.getProductStock()));
        }
    }
}
