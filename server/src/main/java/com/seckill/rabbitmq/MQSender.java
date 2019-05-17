package com.seckill.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendSeckillMessage(SeckillMessage sm) {
        String msg = null;
        try {
            msg = RedisService.beanToString(sm);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (msg != null) {
            log.info("send message:" + msg);
            amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE, msg);
        }
    }
}
