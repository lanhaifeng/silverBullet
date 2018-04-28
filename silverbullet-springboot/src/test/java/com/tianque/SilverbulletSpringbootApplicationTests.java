package com.tianque;

import com.tianque.api.activemq.ActivemqProducer;
import com.tianque.api.activemq.ActivemqPublisher;
import com.tianque.api.hystrix.demo.HystrixDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SilverbulletSpringbootApplicationTests {

	@Autowired
	private ActivemqProducer activemqProducer;

	@Autowired
	private ActivemqPublisher activemqPublisher;

	@Autowired
	private HystrixDemo hystrixDemo;

	@Test
	public void testActivemq() {
		for (int i = 0; i < 1000; i++) {
			activemqProducer.sendMessage(
					"tianque.queue", String.valueOf(i));
		}
	}

	@Test
	public void testHystrix(){
		for (int i = 0; i < 51; i++) {
			hystrixDemo.showHystrix();
		}
	}

}
