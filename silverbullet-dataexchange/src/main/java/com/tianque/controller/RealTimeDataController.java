package com.tianque.controller;

import com.tianque.constant.RequestStatus;
import com.tianque.handler.ModuleDataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 * 数据模拟推送
 */
@RestController
@RequestMapping("/realtime/")
public class RealTimeDataController {
    @Autowired
    ModuleDataHandler moduleDataHandler;
    @RequestMapping("/do/{type}/{dataId}/{operateType}")
    private String rtDataAccept(@PathVariable String type,@PathVariable Long dataId,@PathVariable String operateType){
        moduleDataHandler.handle(type,dataId,operateType);
        return RequestStatus.SUCCESS.toString();
    }
}
