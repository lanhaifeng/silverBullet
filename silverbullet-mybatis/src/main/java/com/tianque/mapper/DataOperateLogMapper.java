package com.tianque.mapper;

import com.tianque.domain.DataOperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 * 数据操作日志类，备用
 */
@Mapper
public interface DataOperateLogMapper{
    @Insert("Insert into DATA_OPERATE_LOGS(id,operate_type,operate_date," +
            "data_type,data_id,create_date,create_user,update_date,update_user) values " +
            "(#{id},#{operateType},#{operateDate},#{dataType},#{dataId},#{createDate},#{createUser}," +
            "#{updateDate},#{updateUser})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true,
            statement = "select replace(sys_guid(), '-', '') as id from dual")
    @Options(useGeneratedKeys=true,keyProperty="id")
    void insert(DataOperateLog user);
}
