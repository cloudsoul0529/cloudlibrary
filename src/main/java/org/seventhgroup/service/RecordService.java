package org.seventhgroup.service;

import org.seventhgroup.dto.PageResult;
import org.seventhgroup.pojo.Record;
import org.seventhgroup.pojo.User;

/**
 * 借阅记录接口
 *
 * @author sooooya838
 */
public interface RecordService {

    /**
     * 新增借阅记录
     *
     * @param record 记录实体
     * @return 影响行数
     */
    Integer addRecord(Record record);

    /**
     * 查询借阅记录
     *
     * @param record   查询条件
     * @param user     当前登录用户
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页结果封装
     */
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);
}