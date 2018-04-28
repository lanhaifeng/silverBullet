package com.tianque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class SilverbulletActivemqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SilverbulletActivemqApplication.class, args);
	}
}
