package com.tianque.test;

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
//        session.recover();
//    }

//    @JmsListener(destination = "tianque.topic", containerFactory="jmsListenerContainer")
//    public void receiveTopic(final TextMessage text, Session session) throws JMSException{
//        try {
//            System.out.println("receiveTopic:"+text.getText().toString());
//        }catch (Exception exception){
//            session.rollback();
//        }
//    }

//
    @JmsListener(destination = "tianque.queue",containerFactory = "jmsListenerContainer1")
    public void receviceMessage1(final TextMessage text, Session session) throws JMSException {
        System.out.println("test方接受："+ text.getText());
        System.out.println("test方当前数量："+ i++);
        session.commit();
    }


}
