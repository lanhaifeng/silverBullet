package com.tianque.api.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by QQ on 2018/3/8.
 * 消息的接收，也就是@JmsListener如果不指定独立的containerFactory的
 * 话是只能消费queue消息的，如果要能够同时接收topic消息，需要给topic对应
 * 的@JmsListener增加containerFactory配置
 */
@Component
public class ActivemqConsumer {

    private int i = 1;
//    @JmsListener(destination = "tianque.queue",containerFactory = "jmsListenerContainer1")
//    public void receviceMessage(final TextMessage text, Session session) throws JMSException {
//        System.out.println("receviceMessage:"+(i++)+"----------"+text.getText().toString());
//        //手动签收ack模式的退回
//        session.recover();
//    }
//
//    @JmsListener(destination = "tianque.queue",containerFactory = "jmsListenerContainer1")
//    public void receviceMessage1(final TextMessage text, Session session) throws JMSException {
//        System.out.println("receviceMessage1:"+(i++)+"----------"+text.getText().toString());
//        //手动签收ack模式的确认
//        text.acknowledge();
//    }


    @JmsListener(destination = "tianque.queue", containerFactory="jmsListenerContainer")
    public void receiveTopic(final TextMessage text, Session session) throws JMSException {
        try {
            System.out.println("activemq方接受："+ text.getText());
            System.out.println("activemq方当前数量："+ i++);
        }catch (Exception exception){
            session.rollback();
        }
    }
}
