package com.tianque.message;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/24.
 */
public class RealTimeDataMessage {
    String operateType;
    String data;

    public RealTimeDataMessage() {
    }

    public RealTimeDataMessage(String operateType, String data) {
        this.operateType = operateType;
        this.data = data;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
