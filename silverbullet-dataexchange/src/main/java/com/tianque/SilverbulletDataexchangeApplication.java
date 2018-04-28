package com.tianque;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.tianque.mapper")
@ComponentScan(basePackages="com.tianque.*.*")
public class SilverbulletDataexchangeApplication {
	public static void main(String[] args) {
		SpringApplication.run(SilverbulletDataexchangeApplication.class, args);
	}
}
