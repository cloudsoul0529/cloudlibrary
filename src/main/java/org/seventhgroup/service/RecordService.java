package org.seventhgroup.service;

import org.seventhgroup.entity.PageResult;
import org.seventhgroup.pojo.Record;
import org.seventhgroup.pojo.User;

/**
 * @author DeadSoup
 * @time 2025/11/19 17:22
 */
public interface RecordService {
    Integer addRecord(Record record);
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);
}
