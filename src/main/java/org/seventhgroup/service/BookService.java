package org.seventhgroup.service;

import org.seventhgroup.dto.PageResult;
import org.seventhgroup.pojo.Book;
import org.seventhgroup.pojo.User;

/**
 * 借阅图书接口
 * @author  Agony123466
 * @time 2025/12/7 16:58
 */
public interface BookService {
    //查询最新上架的图书
    PageResult selectNewBooks();
    //根据id查询图书信息
    Book findById(String id);
    //借阅图书
    Integer borrowBook(Book book);
    //分页查询图书
    PageResult search(Book book, Integer pageNum, Integer pageSize);
    //新增图书
    Integer addBook(Book book);
    //编辑图书信息
    Integer editBook(Book book);
    //查询当前借阅的图书
    PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize, String showType);
    //归还图书
    boolean returnBook(String  id,User user);
    //归还确认
    Integer returnConfirm(String id);
    //批量确认归还
    void batchReturnConfirm(String[] ids);
    //获取未归还数量
    Integer getConfirmCount();
}