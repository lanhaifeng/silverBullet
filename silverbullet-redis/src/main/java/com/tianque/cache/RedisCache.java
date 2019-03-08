package com.tianque.cache;

import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @className:
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-21 10:29
 * @version: 1.0
 */
public interface RedisCache<K,V> extends Cache<K,V>{

    @Nullable
    void setnx(K key,V value);

    @Nullable
    void lpush(K key,V... values);

    @Nullable
    List<String> lrange(K key, long start, long end);

}
