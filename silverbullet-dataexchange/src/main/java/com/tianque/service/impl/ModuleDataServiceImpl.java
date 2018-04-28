package com.tianque.service.impl;

import com.alibaba.fastjson.JSON;
import com.tianque.mapper.ModuleDataMapper;
import com.tianque.service.AbstractDataService;
import com.tianque.service.ModuleDataService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/24.
 */
public class ModuleDataServiceImpl extends AbstractDataService implements ModuleDataService{
    @Autowired
    ModuleDataMapper moduleDataMapper;
    @Override
    public String getDomainByTypeAndId(String type, Long id) {
        return JSON.toJSONString(moduleDataMapper.getMapById(type,id));
    }
}
