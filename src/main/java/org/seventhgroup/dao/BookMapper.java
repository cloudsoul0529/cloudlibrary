package org.seventhgroup.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.seventhgroup.pojo.Book;
import org.springframework.stereotype.Repository;

/**
 * 图书接口
 */
@Mapper
@Repository
public interface BookMapper {

    // 查询新书（按上传时间降序）
    Page<Book> selectNewBooks();

    // 根据id查询图书信息
    Book findById(String id);

    // 分页查询图书
    Page<Book> searchBooks(Book book);

    // 新增图书
    Integer addBook(Book book);

    // 编辑图书信息
    Integer editBook(Book book);

    // 查询借阅但未归还的图书和待归还确认的图书
    Page<Book> selectBorrowed(Book book);

    // 查询借阅但未归还的图书
    Page<Book> selectMyBorrowed(Book book);
}
