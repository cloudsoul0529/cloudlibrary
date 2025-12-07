package org.seventhgroup.dao;

import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
 * 数据统计持久层接口
 * @author su0Tmore
 */
public interface StatsMapper {

    // [su0Tmore] 统计图书总数
    @Select("SELECT COUNT(*) FROM book")
    int selectBookCount();

    // [su0Tmore] 统计用户总数
    @Select("SELECT COUNT(*) FROM user")
    int selectUserCount();

    // [su0Tmore] 统计借阅记录总数
    @Select("SELECT COUNT(*) FROM record")
    int selectRecordCount();

    // [su0Tmore] 联合查询详细借阅数据 (扩展版)
    // 新增查询：ISBN 和 归还时间
    @Select("SELECT record_bookname as bName, " +
            "record_bookisbn as isbn, " +
            "record_borrower as uName, " +
            "record_borrowtime as bTime, " +
            "record_remandtime as rTime " +
            "FROM record")
    List<Map<String, Object>> selectReportData();
}