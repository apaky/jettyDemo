package com.jettyDemo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 保存系统相关配置信息
 */
@Component("sysDao")
public interface SysDao {
    /**
     * 查询system表是否存在
     */
    public int sysTableCount();

    /**
     * 创建系统配置表
     */
    public void createSysTable();

    /**
     * 插入一条sys记录
     */
    public void insert(@Param("name") String name, @Param("value") String value);

    /**
     * 根据属性名称查询值
     */
    public List<String> findValByName(@Param("name") String name);
}
