package com.tianque.cache.api;

import com.tianque.cache.RedisCache;
import com.tianque.cache.redis.jedis.JedisCache;
import com.tianque.cache.redis.lettuce.LettuceCache;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        jedisCache.set(key, value);
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

    @RequestMapping("/minus/{key}")
    public String minus(@PathVariable("key")String key) {
        String valRes;
        try{
            Long valNum = Long.valueOf(jedisCache.get(key));
            if(valNum<=0){
                valRes = "0";
            }else{
                valRes = String.valueOf(valNum-1);
            }
        } catch (ClassCastException e){
            log.error(e.getStackTrace().toString());
            valRes =  "-1";
        }
        jedisCache.set(key,valRes);
        return valRes;
    }
}
