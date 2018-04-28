package com.tianque.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.tianque.constant.DataSourceType;
import com.tianque.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by QQ on 2018/4/24.
 */
@Configuration
public class DataSourceConfig {
//    @ConfigurationProperties(prefix = "spring.datasource.master") // application.properteis中对应属性的前缀
//    public DataSource masterDS() {
//        return DataSourceBuilder.create().build();
//    }
//    @ConfigurationProperties(prefix = "spring.datasource.slave") // application.properteis中对应属性的前缀
//    public DataSource slaveDS() {
//        return DataSourceBuilder.create().build();
//    }
    @Autowired
    private Environment env;

    public DataSource masterDS() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.master.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.master.jdbc-url"));
        props.put("username", env.getProperty("spring.datasource.master.username"));
        props.put("password", env.getProperty("spring.datasource.master.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    public DataSource slaveDS() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.slave.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.slave.jdbc-url"));
        props.put("username", env.getProperty("spring.datasource.slave.username"));
        props.put("password", env.getProperty("spring.datasource.slave.password"));
        props.put("type", env.getProperty("spring.datasource.slave.type"));
        return DruidDataSourceFactory.createDataSource(props);
    }
    @Bean
    @Primary
    public DynamicDataSource dataSource() throws Exception  {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(5);
        dsMap.put(DataSourceType.masterDS, masterDS());
        dsMap.put(DataSourceType.slaveDS, slaveDS());
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDS());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }
}
