package com.tianque.cache.redis.jedis;

import cn.hutool.core.lang.Assert;
import com.tianque.cache.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.params.SetParams;

import java.util.List;

/**
 * @className:
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-21 10:30
 * @version: 1.0
 */
@Slf4j
@Service("jedisCache")
public class JedisCache implements RedisCache<String,String> {

    @Autowired
    JedisSentinelPool jedisSentinelPool;

    public Jedis getClient(){
        return jedisSentinelPool.getResource();
    }

    @Override
    public void setnx(String key, String value) {
        try (Jedis jedis = getClient()){
            jedis.setnx(key,value);
        }
    }

    @Override
    public void set(String key, String value) {
        try (Jedis jedis = getClient()) {
            jedis.set(key, value);
        }
    }

    @Override
    public void set(String key, String value, int expire) {
        try (Jedis jedis = getClient()) {
            Assert.notNull(key);
            if (expire < 0) {
                set(key, value);
                return;
            }
            jedis.set(key, value, SetParams.setParams().ex(expire));
        }
    }

    @Override
    public String get(String key) {
        try (Jedis jedis = getClient()) {
            return jedis.get(key);
        }
    }

    @Override
    public void lpush(String key, String... values) {
        try (Jedis jedis = getClient()) {
            jedis.lpush(key, values);
        }
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        try (Jedis jedis = getClient()) {
            return jedis.lrange(key, start, end);
        }
    }

}
