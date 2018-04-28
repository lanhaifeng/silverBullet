package com.tianque.service;

import com.alibaba.fastjson.JSON;
import com.tianque.mapper.AbstractDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 */
@Service("abstractDataService")
public abstract class AbstractDataService extends DataService{
    @Autowired
    AbstractDataMapper abstractDataMapper;
    public String getDomainById(String tableName,Long id) {
        return JSON.toJSONString(abstractDataMapper.getMapById(tableName,id));
    }
}
