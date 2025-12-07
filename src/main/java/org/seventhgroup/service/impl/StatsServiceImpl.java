package org.seventhgroup.service.impl;

import org.seventhgroup.dao.StatsMapper;
import org.seventhgroup.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsMapper statsMapper;

    // [su0Tmore] 负责组装三个维度的统计数据
    @Override
    public Map<String, Integer> getStatsData() {
        Map<String, Integer> dataMap = new HashMap<>();
        // 分别查询并放入 map
        dataMap.put("book", statsMapper.selectBookCount());
        dataMap.put("user", statsMapper.selectUserCount());
        dataMap.put("record", statsMapper.selectRecordCount());
        return dataMap;
    }

    // [su0Tmore] 负责获取原始报表数据
    @Override
    public List<Map<String, Object>> getReportList() {
        return statsMapper.selectReportData();
    }
}