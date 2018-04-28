package com.tianque.config;

import com.tianque.constant.DataProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 * mybatis 配置文件
 */
@Configuration
public class MybatisConfig {
    @Autowired
    private DataProperties dataProperties;
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.tianque.domain");
        mapperScannerConfigurer.setAddToConfig(true);
        return mapperScannerConfigurer;
    }
}
