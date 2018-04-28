package com.tianque.mapper1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by QQ on 2018/4/25.
 */
@Mapper
public interface BaseInfoDao1 {
    @Select("SELECT * from Baseinfo where id <100")
    List<Map> selectAll();
}
