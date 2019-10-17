package com.tianque.cache.redis.lettuce;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @className:
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-24 17:51
 * @version: 1.0
 */
public class LettucePoolConfig extends GenericObjectPoolConfig {

    public LettucePoolConfig(){
        // defaults to make your life with connection pool easier :)
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }
}
