package com.tianque.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/20.
 * 数据实时推送-消费者
 */
@Component
public class DataRealtimePushConsumer {
    @JmsListener(destination = "tianque.dataexchange.queue", containerFactory="jmsListenerContainer")
    public void receiveTopic(final TextMessage text, Session session) throws JMSException {
        try {
            System.out.println("activemq方接受："+ text.getText());
        }catch (Exception exception){
            session.rollback();
        }
    }
}
