package com.tianque.mapper;

import com.tianque.constant.DataSourceType;
import com.tianque.datasource.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by QQ on 2018/4/25.
 */
@Mapper
public interface BaseInfoDao {
    @DS(DataSourceType.slaveDS)
    @Select("SELECT * from Baseinfo where id <100")
    List<Map> selectAll();
    @Select("SELECT * from Baseinfo where id <100")
    List<Map> selectAll1();
}
