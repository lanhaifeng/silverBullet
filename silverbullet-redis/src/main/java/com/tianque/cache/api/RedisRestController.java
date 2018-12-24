package com.tianque.cache.api;

import com.tianque.cache.RedisCache;
import com.tianque.cache.redis.jedis.JedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: redisRestController
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-24 9:50
 * @version: 1.0
 */
@RestController
@RequestMapping("/redisManage")
public class RedisRestController implements RedisCache<String,String> {

    @Autowired
    JedisCache jedisCache;

    @Override
    @RequestMapping("/setnx")
    public void setnx(String key, String value) {
        jedisCache.setnx(key, value);
    }

    @Override
    @RequestMapping("/set")
    public void set(String key, String value) {
        jedisCache.set(key, value);
    }

    @Override
    @RequestMapping("/setexpire")
    public void set(String key, String value, int expire) {
        jedisCache.set(key, value,expire);
    }

    @Override
    @RequestMapping("/get/")
    public String get(String key) {
        return jedisCache.get(key);
    }

}
