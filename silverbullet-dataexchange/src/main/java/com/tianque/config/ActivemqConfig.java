package com.tianque.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import java.util.Properties;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/20.
 * 消息队列配置类
 */
@Configuration
public class ActivemqConfig {
    /**
     * 预读策略配置类
     * @return
     */
    @Bean
    public ActiveMQPrefetchPolicy activeMQPrefetchPolicy(){
        ActiveMQPrefetchPolicy activeMQPrefetchPolicy = new ActiveMQPrefetchPolicy();
        activeMQPrefetchPolicy.setQueuePrefetch(1000);
        return activeMQPrefetchPolicy;
    }
    /**
     * 重新投递策略配置类
     * 测试结果，重投只能消费者
     * @return
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy(){
        RedeliveryPolicy  redeliveryPolicy=   new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次,超出重发次数
        redeliveryPolicy.setMaximumRedeliveries(3);
        //重发时间间隔,默认为1000毫秒
        redeliveryPolicy.setInitialRedeliveryDelay(2000);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory (@Value("${spring.activemq.broker-url}")String url,
                                                                RedeliveryPolicy redeliveryPolicy, ActiveMQPrefetchPolicy activeMQPrefetchPolicy){
        Properties props = new Properties();
        /***
         * 属性类可以配置绝大部分的配置项
         * queue预读，单次消费获取的消息数量
         */
//        props.setProperty("prefetchPolicy.queuePrefetch", "1000");

        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(
                        "admin",
                        "admin",
                        url);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        //activeMQ预取策略
        activeMQConnectionFactory.setPrefetchPolicy(activeMQPrefetchPolicy);
        activeMQConnectionFactory.setProducerWindowSize(10000);
//        activeMQConnectionFactory.setProperties(props);
        return activeMQConnectionFactory;
    }
    /**
     * 监听容器工厂类，可以为所有的消费者监听器配置同一个工厂类，也可以为每个监听配置单独的工厂类
     * 注意：大部分的针对消息消费，重投方面的配置优化都可以在该类中设置
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainer(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        /** 默认是false
         *  在使用Spring JMS的时候，主题（Topic）和队列消息的主要差异体现在JmsTemplate中"pubSubDomain"是否设置为True。
         *  如果为True，则是Topic；如果是false或者默认，则是queue。
         *   注意：bean.setPubSubDomain(true);后该工厂类只能指定给topic接受，不能指定给queue使用，同理false
         **/
        bean.setConnectionFactory(activeMQConnectionFactory);
        bean.setPubSubDomain(false);
        bean.setSessionTransacted(true);
        /** 设置签收模式，注意：如果此session为事务类型,用户指定的ACK_MODE将被忽略,而强制使用"SESSION_TRANSACTED"类型;如果session非事务类型时,也将不能将 ACK_MODE设定为"SESSION_TRANSACTED"
         解决方案：一般使用（CLIENT_ACKNOWLEDGE）
         1、AUTO_ACKNOWLEDGE = 1    自动确认
         2、CLIENT_ACKNOWLEDGE = 2    客户端手动确认
         3、DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
         4、SESSION_TRANSACTED = 0    事务提交并确认
         5、INDIVIDUAL_ACKNOWLEDGE = 4    单条消息确认（自定义ACK_MODE）
         */
//        bean.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return bean;
    }
}
