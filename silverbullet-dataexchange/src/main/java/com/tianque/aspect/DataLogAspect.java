package com.tianque.aspect;

import com.tianque.domain.DataOperateLog;
import com.tianque.service.DataOperateLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/24.
 * 日志切面
 */
@Aspect
@Component
public class DataLogAspect {
    @Autowired
    DataOperateLogService dataOperateLogService;
    @Pointcut("execution(public * com.tianque.service.impl.DataMockServiceImpl.doMock(..))")
    public void addAspect(){}

    @Before("addAspect()")
    public void beforeMethodAddLog(JoinPoint joinPoint){
        Object[] args= joinPoint.getArgs();
        if(args.length == 3){
            String type = String.valueOf(args[0]);
            Long id = (Long)args[1];
            String operateType = (String)args[2];
            DataOperateLog log = new DataOperateLog(type, LocalDate.now(),id,operateType);
            log.setCreateDate(LocalDate.now());
            log.setCreateUser("SYSTEM");
            dataOperateLogService.addLog(log);
        }
    }
}
