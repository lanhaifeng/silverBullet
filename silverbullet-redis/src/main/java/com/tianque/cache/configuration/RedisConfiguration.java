package com.tianque.cache.configuration;

import com.tianque.cache.properties.RedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @className: redisConfiguration
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-21 13:51
 * @version: 1.0
 */
@Configuration
public class RedisConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);


    private final String MASTER_NAME = "mymaster";

    @Autowired
    RedisProperties redisProperties;

    @Bean
    @Primary
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMills());
        return jedisPoolConfig;
    }

    @Bean
    @ConditionalOnBean(JedisPoolConfig.class)
    public JedisSentinelPool jedisSentinelPool(JedisPoolConfig jedisPoolConfig){
        if(logger.isDebugEnabled()){
            logger.debug("JedisSentinelPool is init success for " + redisProperties.getSentinels());
        }
        Assert.notNull(redisProperties.getSentinels(),"system can not read redis sentinels set from reids.properties");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(MASTER_NAME,redisProperties.getSentinels(),jedisPoolConfig);
        return  jedisSentinelPool;
    }

}
