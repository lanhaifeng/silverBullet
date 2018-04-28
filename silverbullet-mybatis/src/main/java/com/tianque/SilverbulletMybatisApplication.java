package com.tianque;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.tianque.mapper")
public class SilverbulletMybatisApplication {
	public static void main(String[] args) {
		SpringApplication.run(SilverbulletMybatisApplication.class, args);
	}
}
