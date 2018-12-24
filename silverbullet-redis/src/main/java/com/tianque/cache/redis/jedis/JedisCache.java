package com.tianque.cache.redis.jedis;

import cn.hutool.core.lang.Assert;
import com.tianque.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.params.SetParams;

/**
 * @className:
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-21 10:30
 * @version: 1.0
 */
@Service("jedisCache")
public class JedisCache implements RedisCache<String,String> {

    @Autowired
    JedisSentinelPool jedisSentinelPool;

    public Jedis getClient(){
        return jedisSentinelPool.getResource();
    }

    @Override
    public void setnx(String key, String value) {
        getClient().setnx(key,value);
    }

    @Override
    public void set(String key, String value) {
        getClient().set(key,value);
    }

    @Override
    public void set(String key, String value, int expire) {
        Assert.notNull(key);
        if(expire<0){
            set(key,value);
            return;
        }
        getClient().set(key,value, SetParams.setParams().ex(expire));
    }

    @Override
    public String get(String key) {
        return getClient().get(key);
    }

}
