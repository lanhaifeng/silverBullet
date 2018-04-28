package com.tianque.mapper;

import com.tianque.constant.TypeSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.text.MessageFormat;
import java.util.HashMap;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/24.
 * 所有模塊数据的数据接口
 */
@Mapper
public interface ModuleDataMapper {
    @SelectProvider(type = ModuleSql.class, method = "getById")
    public HashMap<String,Object> getMapById(String type, Long id);

    class ModuleSql{
        public String getById(String type,Long id){
            return MessageFormat.format(TypeSql.getSql(type),String.valueOf(id));
        }
    }
}
