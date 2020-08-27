package com.ldh.order.message;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 发送MQ消息 myMq
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class mqReseiverTest {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendTest() {
        amqpTemplate.convertAndSend("myMq", "halo guoguo  mq");
    }

    @Test
    public void GuosendTest() {
        amqpTemplate.convertAndSend("myOrder","Guo","halo guoguo  mq");
    }

    @Test
    public void BhqsendTest() {
        //myOrder 表示交换器组名
        //Bhq  这个组中的key值

        amqpTemplate.convertAndSend("myOrder", "Bhq","halo BHQ  mq");
    }
}