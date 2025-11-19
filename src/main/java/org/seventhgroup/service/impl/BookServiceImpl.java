package org.seventhgroup.service.impl;

import org.seventhgroup.entity.PageResult;
import org.seventhgroup.pojo.Book;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.BookService;

/**
 * @author DeadSoup
 * @time 2025/11/19 17:06
 */
public class BookServiceImpl implements BookService {
    @Override
    public Book findById(String id) {
        return null;
    }

    @Override
    public Integer borrowBook(Book book) {
        return 0;
    }

    @Override
    public Integer addBook(Book book) {
        return 0;
    }

    @Override
    public Integer editBook(Book book) {
        return 0;
    }

    @Override
    public PageResult searchBorrowed() {
        return null;
    }

    @Override
    public boolean returnBook(String id, User user) {
        return false;
    }

    @Override
    public Integer returnConfirm(String id) {
        return 0;
    }
}
