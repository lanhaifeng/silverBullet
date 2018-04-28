package com.tianque.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tianque.constant.DataSourceType;
import com.tianque.datasource.DS;
import com.tianque.mapper.BaseInfoDao;
import com.tianque.mapper.DataOperateLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by QQ on 2018/4/25.
 */
@RequestMapping("/mybaits")
@RestController
@EnableAutoConfiguration
public class MybatisController {
    @Autowired
    BaseInfoDao baseInfoDao;
    @Autowired
    DataOperateLogMapper dataOperateLogMapper;
    @DS(DataSourceType.masterDS)
    @RequestMapping("/do")
    public String doTest(){
        Page page = PageHelper.startPage(1, 20, true);
        List<Map> maps =  baseInfoDao.selectAll();
        System.out.println(page.getTotal());
        return JSON.toJSONString(maps);
    }
}
