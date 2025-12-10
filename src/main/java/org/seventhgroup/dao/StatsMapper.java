package org.seventhgroup.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据统计持久层接口
 * @author su0Tmore
 */
@Mapper
@Repository
public interface StatsMapper {

    // [su0Tmore] 统计图书总数
    int selectBookCount();

    // [su0Tmore] 统计用户总数
    int selectUserCount();

    // [su0Tmore] 统计借阅记录总数
    int selectRecordCount();

    // [su0Tmore] 联合查询详细借阅数据 (扩展版)
    // 新增查询：ISBN 和 归还时间
    List<Map<String, Object>> selectReportData();
}