package com.tianque.service;

import com.tianque.domain.DataOperateLog;

import java.util.List;

/**
 * Created by QQ on 2018/4/23.
 */
public interface DataOperateLogService {
    void addLog(DataOperateLog dataOperateLog);
    List<DataOperateLog> getAllLogs();

}
