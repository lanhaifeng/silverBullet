package com.tianque.cache.redis.lettuce;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import java.util.Set;

/**
 * @className:
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-24 17:22
 * @version: 1.0
 */
@Slf4j
public class LettucePool {

    /**
     * @Description redis-sentinel://{}/0#{}
     * {0}占位host:port,host:port
     * {1}占位mastername
     * @Date
     **/
    public static final String REDISURI_FORMAT = "redis-sentinel://{}/0#{}";

    GenericObjectPool<StatefulRedisConnection<String, String>> pool = null;

    public LettucePool(String host, int port,GenericObjectPoolConfig poolConfig){
        RedisClient client = RedisClient.create(RedisURI.create(host, port));
        pool = ConnectionPoolSupport
                .createGenericObjectPool(() -> client.connect(), poolConfig);
    }

    public LettucePool(Set<String> hostPorts, String masterName, GenericObjectPoolConfig poolConfig){
        RedisClient client = RedisClient.create(StrUtil.format(REDISURI_FORMAT, CollectionUtil.join(hostPorts,","),masterName));
        pool = ConnectionPoolSupport
                .createGenericObjectPool(() -> client.connect(), poolConfig);
    }

    public StatefulRedisConnection<String, String> getConnection() throws Exception{
        StatefulRedisConnection<String, String> connection = null;
        try{
            connection =  pool.borrowObject();
        }catch (Exception e){
            log.error("LettuceSentinelPool get connection is error");
            throw e;
        }
        return connection;
    }

    public RedisCommands getSyncCommands(StatefulRedisConnection<String, String> connection){
        Assert.isTrue(connection.isOpen());
        RedisCommands<String, String> commands = connection.sync();
        return commands;
    }

    public RedisCommands getSyncCommands() throws Exception{
        return this.getSyncCommands(getConnection());
    }

}
