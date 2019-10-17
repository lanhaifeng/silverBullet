package com.tianque.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Set;

/**
 * @className: properties
 * @descripton: TODO
 * @author: mr.0
 * @date: 2018-12-21 14:21
 * @version: 1.0
 */
@Data
@Configuration
@PropertySource("classpath:redis.properties")
@ConfigurationProperties(prefix = "silver.redis")
public class RedisProperties {

    private Integer mode;

    private Integer maxIdle;

    private Integer minIdle;

    private Integer maxTotal;

    private Integer maxWaitMills;

    private Set<String> sentinels;

    private String masterName;

}
