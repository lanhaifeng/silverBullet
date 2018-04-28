package com.tianque.taskTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by QQ on 2018/4/20.
 */
@Component
public class DataCollectTaskTimer implements DataTaskTimer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //
    @Scheduled(cron = "0 0 1 * * ?")
    public void addCollect() {
    }
}
