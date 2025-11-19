package org.seventhgroup.service;

import org.seventhgroup.entity.PageResult;
import org.seventhgroup.pojo.Book;
import org.seventhgroup.pojo.User;

/**
 * 借阅图书接口
 * @author Deadsoup
 * @time 2025/11/19 16:58
 */
public interface BookService {
    Book findById(String id);
    Integer borrowBook(Book book);
    Integer addBook(Book book);
    Integer editBook(Book book);
    PageResult searchBorrowed();
    boolean returnBook(String id, User user);
    Integer returnConfirm(String id);
}
