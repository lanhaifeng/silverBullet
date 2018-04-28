package com.tianque.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.text.MessageFormat;
import java.util.HashMap;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 */
@Mapper
public interface AbstractDataMapper {
    @SelectProvider(type = BaseSql.class, method = "getById")
    public HashMap<String,Object> getMapById(String tableName,Long id);

    class BaseSql{
        private final String selectAllSql = "select * from {0} where id = {1}";
        public String getById(String tableName,Long id){
            return MessageFormat.format(selectAllSql,tableName,String.valueOf(id));
        }
    }
}
