package org.seventhgroup.service;

import java.util.Map;
import java.util.List;

public interface StatsService {
    // [su0Tmore] 获取看板统计数据
    Map<String, Integer> getStatsData();

    // [su0Tmore] 获取报表列表数据
    List<Map<String, Object>> getReportList();
}