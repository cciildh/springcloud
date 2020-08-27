package com.ldh.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收mq消息  myMq
 */

@Component
@Slf4j
public class mqReseiver {
    //1、自动创建队列
//    @RabbitListener(queuesToDeclare = @Queue("myMq"))
    //2、自动创建，Exchange(交换器）和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myMq"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String msg) {
        log.info("MyMQ:{}", msg);
        System.out.println(msg);
    }

    //交换器分组模式现实消息指定服务发送（订阅模式DIRECT)

    /**
     * @RabbitListener bindings:绑定队列
     * @QueueBinding value:绑定队列的名称 exchange:配置交换器 key =订阅的消息key
     * @Queue value:配置队列名称 autoDelete:是否是一个可删除的临时队列
     * @Exchange value:为交换器起个名称 type:指定具体的交换器类型
     *
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "GuoMq", autoDelete = "true"),
            exchange = @Exchange(value = "myOrder",type = ExchangeTypes.DIRECT),
            key = "Guo"//表示只订阅guoguo发送的消息
    ))
    public void GUOprocess(String msg) {
        log.info("GuoMQ:{}", msg);
        System.out.println(msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "BhqMq",autoDelete = "true"),
            exchange = @Exchange(value = "myOrder",type = ExchangeTypes.DIRECT),
            key = "Bhq"
    ))
    public void BHQprocess(String msg) {
        log.info("BhqMQ:{}", msg);
        System.out.println(msg);
    }
}
