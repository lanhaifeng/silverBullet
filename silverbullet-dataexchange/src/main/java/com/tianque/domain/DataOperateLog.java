package com.tianque.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * linlinan
 * Created by linlinan on 2018/4/23.
 * 数据操作备用日志实体类，用于做数据补偿机制
 */
public class DataOperateLog extends BaseDomain{
    private String dataType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate operateDate;
    private Long dataId;
    private String operateType;
    public DataOperateLog(){}
    public DataOperateLog(String dataType,LocalDate operateDate,Long dataId,String operateType){
        this.dataType = dataType;
        this.dataId = dataId;
        this.operateType = operateType;
        this.operateDate = operateDate;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public LocalDate getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(LocalDate operateDate) {
        this.operateDate = operateDate;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}
