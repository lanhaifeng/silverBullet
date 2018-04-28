package com.tianque.mapper;

import com.tianque.domain.DataOperateLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author linlinan
 * Created by linlinan on 2018/4/23.
 * 数据操作日志类，备用
 */
@Mapper
public interface DataOperateLogMapper{
    @Select("Select * from DATA_OPERATE_LOGS")
//    @Results({
//            @Result(property = "operateType",  column = "operate_type"),
//            @Result(property = "operateDate", column = "operate_date"),
//            @Result(property = "dataType", column = "data_type"),
//            @Result(property = "dataId", column = "data_id"),
//            @Result(property = "createDate", column = "create_date"),
//            @Result(property = "createUser", column = "create_user"),
//            @Result(property = "updateDate", column = "update_date"),
//            @Result(property = "updateUser", column = "update_user")
//    })
    List<DataOperateLog> selectAll();

    @Insert("Insert into DATA_OPERATE_LOGS(id,operate_type,operate_date," +
            "data_type,data_id,create_date,create_user,update_date,update_user) values " +
            "(#{id},#{operateType},#{operateDate},#{dataType},#{dataId},#{createDate},#{createUser}," +
            "#{updateDate},#{updateUser})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true,
            statement = "select replace(sys_guid(), '-', '') as id from dual")
    @Options(useGeneratedKeys=true,keyProperty="id")
    void insert(DataOperateLog user);
}
