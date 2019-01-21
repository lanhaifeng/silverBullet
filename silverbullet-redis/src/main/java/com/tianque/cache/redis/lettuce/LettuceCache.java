package com.tianque.cache.redis.lettuce;

import com.tianque.cache.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className:
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-24 18:16
 * @version: 1.0
 */
@Slf4j
@Service("lettuceCache")
public class LettuceCache implements RedisCache<String,String> {

    @Autowired
    LettucePool lettucePool;

    @Override
    public void setnx(String key, String value) {

    }

    @Override
    public void lpush(String key, String... values) {

    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return null;
    }

    @Override
    public void set(String key, String value) {
        try{
            lettucePool.getSyncCommands().set(key,value);
        }catch (Exception e){
            log.error("lettuceCache set error");
        }
    }

    @Override
    public void set(String key, String value, int expire) {

    }

    @Override
    public String get(String key) {
        return null;
    }

}
