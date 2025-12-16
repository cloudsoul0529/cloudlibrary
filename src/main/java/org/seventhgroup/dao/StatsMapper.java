package org.seventhgroup.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface StatsMapper {

    // [su0Tmore] 统计每日借阅趋势
    @Select("SELECT temp.date_str as name, COUNT(*) as value FROM (" +
            "  SELECT LEFT(record_borrowtime, 10) as date_str FROM record " +
            "  WHERE record_borrowtime IS NOT NULL AND record_borrowtime != '' " +
            "  UNION ALL " +
            "  SELECT LEFT(book_borrowtime, 10) as date_str FROM book " +
            "  WHERE book_status = '1' AND book_borrowtime IS NOT NULL AND book_borrowtime != '' " +
            ") as temp " +
            "GROUP BY temp.date_str " +
            "ORDER BY temp.date_str ASC")
    List<Map<String, Object>> selectDailyTrend();

    // [su0Tmore] 统计热门图书 Top 5
    @Select("SELECT temp.book_name as name, COUNT(*) as value FROM (" +
            "  SELECT record_bookname as book_name FROM record " +
            "  UNION ALL " +
            "  SELECT book_name as book_name FROM book " +
            "  WHERE book_status = '1' " +
            ") as temp " +
            "GROUP BY temp.book_name " +
            "ORDER BY value DESC " +
            "LIMIT 5")
    List<Map<String, Object>> selectTop5Books();

    // [su0Tmore] 基础数据统计
    @Select("SELECT COUNT(*) FROM book")
    int selectBookCount();

    @Select("SELECT COUNT(*) FROM user")
    int selectUserCount();

    @Select("SELECT COUNT(*) FROM record")
    int selectRecordCount();

    // [su0Tmore] 报表导出数据查询
    @Select("SELECT record_bookname as bName, " +
            "record_bookisbn as isbn, " +
            "record_borrower as uName, " +
            "record_borrowtime as bTime, " +
            "record_remandtime as rTime " +
            "FROM record")
    List<Map<String, Object>> selectReportData();
}