package com.tianque.cache.configuration;

import com.tianque.cache.properties.RedisProperties;
import com.tianque.cache.redis.lettuce.LettucePool;
import com.tianque.cache.redis.lettuce.LettucePoolConfig;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Configuration
public class RedisConfiguration {

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
        if(log.isDebugEnabled()){
            log.debug("JedisSentinelPool is init success for " + redisProperties.getSentinels());
        }
        Assert.notNull(redisProperties.getSentinels(),"system can not read redis sentinels set from reids.properties");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(redisProperties.getMasterName(),redisProperties.getSentinels(),jedisPoolConfig);
        return  jedisSentinelPool;
    }

    @Bean
    @Primary
    public LettucePoolConfig lettucePoolConfig(){
        LettucePoolConfig lettucePoolConfig = new LettucePoolConfig();
        lettucePoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        lettucePoolConfig.setMinIdle(redisProperties.getMinIdle());
        lettucePoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        lettucePoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMills());
        return lettucePoolConfig;
    }

    @Bean
    @ConditionalOnBean(LettucePoolConfig.class)
    public LettucePool lettucePool(LettucePoolConfig lettucePoolConfig){
        if(log.isDebugEnabled()){
            log.debug("JedisSentinelPool is init success for " + redisProperties.getSentinels());
        }
        Assert.notNull(redisProperties.getSentinels(),"system can not read redis sentinels set from reids.properties");
        LettucePool jedisSentinelPool = new LettucePool(redisProperties.getSentinels(),redisProperties.getMasterName(),lettucePoolConfig);
        return  jedisSentinelPool;
    }

}
