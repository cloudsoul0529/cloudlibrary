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

    // [su0Tmore] 统计热门图书
    // 逻辑解释：只要 record 表里有一行，就代表被借过一次（不管还不还，都算热度）
    @Select("SELECT record_bookname as name, COUNT(*) as value " +
            "FROM record " +
            "GROUP BY record_bookname " +
            "ORDER BY value DESC " +
            "LIMIT 5")
    List<Map<String, Object>> selectTop5Books();

    /**
     * [su0Tmore] 统计每日借阅趋势
     * 逻辑：LEFT(time, 10) 截取到 YYYY-MM-DD，按天分组
     */
    @Select("SELECT LEFT(record_borrowtime, 10) as name, COUNT(*) as value " +
            "FROM record " +
            "WHERE record_borrowtime IS NOT NULL AND record_borrowtime != '' " +
            "GROUP BY LEFT(record_borrowtime, 10) " +
            "ORDER BY name ASC")
    List<Map<String, Object>> selectDailyTrend();
}