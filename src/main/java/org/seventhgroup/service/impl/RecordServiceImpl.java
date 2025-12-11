package org.seventhgroup.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.seventhgroup.dao.RecordMapper;
import org.seventhgroup.dto.PageResult;
import org.seventhgroup.pojo.Record;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 借阅记录实现类
 *
 * @author sooooya838
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    /**
     * 新增借阅记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    /**
     * 分页查询借阅记录
     */
    @Override
    public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {
        // 1. 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 2. 权限控制：如果不是管理员，强制只查自己的借阅记录
        if (user != null && !"ADMIN".equals(user.getRole())) {
            record.setBorrower(user.getName());
        }

        // 3. 执行查询
        Page<Record> page = recordMapper.searchRecords(record);

        // 4. 封装结果
        return new PageResult(page.getTotal(), page.getResult());
    }
}