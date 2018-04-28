//package com.tianque.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * Created by QQ on 2018/4/25.
// */
//@Configuration
//@MapperScan(basePackages = {"com.tianque.mapper1"}, sqlSessionFactoryRef = "sqlSessionFactory2")
//public class MybatisConfig2 {
//    @Autowired
//    @Qualifier("slaveDS")
//    private DataSource ds2;
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory2() throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(ds2); // 使用titan数据源, 连接titan库
//        return factoryBean.getObject();
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory2()); // 使用上面配置的Factory
//        return template;
//    }
//}
