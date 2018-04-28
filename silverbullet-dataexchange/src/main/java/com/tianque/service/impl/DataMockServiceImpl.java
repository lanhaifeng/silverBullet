package com.tianque.service.impl;

import com.tianque.constant.TableName;
import com.tianque.service.AbstractDataService;
import com.tianque.service.DataMockService;
import com.tianque.service.DataOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 */
@Service
public class DataMockServiceImpl extends AbstractDataService implements DataMockService{
    @Autowired
    DataOperateLogService dataOperateLogService;

    @Override
    public String doMock(String type, Long id,String operateType) {
        return getDomainById(TableName.getTableName(type),id);
    }
}
