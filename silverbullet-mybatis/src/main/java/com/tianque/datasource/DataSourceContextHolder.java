package com.tianque.datasource;

import com.tianque.constant.DataSourceType;

/**
 * Created by QQ on 2018/4/25.
 */
public class DataSourceContextHolder {
    public static final DataSourceType DEFAULT_DS = DataSourceType.masterDS;
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();
    static{
        contextHolder.set(DEFAULT_DS);
    }
    // 设置数据源名
    public static void setDB(DataSourceType dbType) {
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static DataSourceType getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        contextHolder.remove();
    }
}
