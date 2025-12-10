package org.seventhgroup.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.seventhgroup.pojo.Record;
import org.springframework.stereotype.Repository;

/**
 * 借阅记录数据访问层接口
 * @author sooooya838
 */
@Mapper
@Repository
public interface RecordMapper {

    //新增借阅记录
    Integer addRecord(Record record);

    //查询借阅记录
    Page<Record> searchRecords(Record record);
}