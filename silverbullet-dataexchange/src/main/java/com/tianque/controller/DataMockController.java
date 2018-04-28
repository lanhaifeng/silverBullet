package com.tianque.controller;

import com.tianque.constant.OperateType;
import com.tianque.service.DataMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 * 数据模拟推送
 */
@RestController
@RequestMapping("/dataMock")
public class DataMockController {
    @Autowired
    DataMockService dataMockService;
    @RequestMapping("/do")
    private String dataMockAccept(){
        return dataMockService.doMock("issue",4125L, OperateType.ADD.getEname());
    }
}
