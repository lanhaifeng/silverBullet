package com.tianque.api.hystrix.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

/**
 * Created by QQ on 2018/3/9.
 */
@Service
public class HystrixDemo {

    @HystrixCommand(fallbackMethod = "fallbackHystrix", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100000")
            , @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")})
    public void showHystrix(){
        try {
            Thread.sleep(1);
            System.out.println("abc");
            throw new Exception();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fallbackHystrix(){
        System.out.println("1234");
    }
}
