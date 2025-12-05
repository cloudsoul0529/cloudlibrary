package org.seventhgroup.service;

import org.seventhgroup.entity.PageResult;
import org.seventhgroup.pojo.Record;
import org.seventhgroup.pojo.User;

/**
 * @author DeadSoup
 * @time 2025/11/19 17:22
 */
public interface RecordService {
    //新增借阅记录
    Integer addRecord(Record record);
    //查询借阅记录
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);
}