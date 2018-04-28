package com.tianque.service.impl;

import com.tianque.domain.DataOperateLog;
import com.tianque.mapper.DataOperateLogMapper;
import com.tianque.service.DataOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 * 日志service
 */
@Service("dataOperateLogService")
public class DataOperateLogServiceImpl implements DataOperateLogService {
    @Autowired
    DataOperateLogMapper dataOperateLogMapper;
    @Override
    public void addLog(DataOperateLog dataOperateLog) {
        dataOperateLogMapper.insert(dataOperateLog);
    }

    @Override
    public List<DataOperateLog> getAllLogs() {
        return dataOperateLogMapper.selectAll();
    }
}
