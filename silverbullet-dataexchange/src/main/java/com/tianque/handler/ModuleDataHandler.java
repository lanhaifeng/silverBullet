package com.tianque.handler;

import com.alibaba.fastjson.JSON;
import com.tianque.message.RealTimeDataMessage;
import com.tianque.service.ModuleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by QQ on 2018/4/24.
 */
@Component
public abstract class ModuleDataHandler extends AbstratDataHandler{
    @Autowired
    ModuleDataService moduleDataService;
    RealTimeDataMessage realTimeDataMessage;
    public String handle(String type,Long id,String operateType) {
        return JSON.toJSONString(realTimeDataMessage = new RealTimeDataMessage(operateType,moduleDataService.getDomainByTypeAndId(type,id)));
    }
}
