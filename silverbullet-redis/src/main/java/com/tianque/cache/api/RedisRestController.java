package com.tianque.cache.api;

import com.tianque.cache.RedisCache;
import com.tianque.cache.redis.jedis.JedisCache;
import com.tianque.cache.redis.lettuce.LettuceCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    LettuceCache lettuceCache;

    @Override
    @RequestMapping("/setnx/{key}/{value}")
    public void setnx(@PathVariable("key")String key, @PathVariable("value")String value) {
        jedisCache.setnx(key, value);
    }

    @Override
    @RequestMapping("/set/{key}/{value}")
    public void set(@PathVariable("key")String key, @PathVariable("value")String value) {
        lettuceCache.set(key, value);
    }

    @Override
    @RequestMapping("/setexpire/{key}/{value}/{expire}")
    public void set(@PathVariable("key")String key, @PathVariable("value")String value, @PathVariable("expire")int expire) {
        jedisCache.set(key, value, expire);
    }

    @Override
    @RequestMapping("/get/{key}")
    public String get(@PathVariable("key")String key) {
        return jedisCache.get(key);
    }

    @Override
    @RequestMapping("/lpush/{key}/{value}")
    public void lpush(@PathVariable("key")String key, @PathVariable("value")String... values) {
        jedisCache.lpush(key,values);
    }

    @Override
    @RequestMapping("/lrange/{key}/{start}/{end}")
    public List<String> lrange(@PathVariable("key")String key, @PathVariable("start")long start, @PathVariable("end")long end) {
        return jedisCache.lrange(key,start,end);
    }
}
