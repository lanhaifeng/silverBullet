package com.tianque.api.activemq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * Created by QQ on 2018/3/8.
 */
@Service("activemqPublisher")
public class ActivemqPublisher {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    public void pubishMessage(String topicName,String message){
        Destination destination = new ActiveMQTopic(topicName);
        jmsMessagingTemplate.convertAndSend(destination,message);
    }
}
