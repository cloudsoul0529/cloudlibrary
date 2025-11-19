package org.seventhgroup.controller;

import org.seventhgroup.service.BookService;

/**
 * @author cloudsoul-ZX
 * @time 2025.11.16
 * */
public class BookController {
    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    //新书推荐模块
    public void selectNewBooks(){

    }

    //图书借阅模块，包括查询、新增、编辑、借阅四个功能
    //查询图书
    public void searchBook(){

    }
    //新增图书（管理员）
    public void addBook(){

    }
    //编辑图书（管理员）
    public void editBook(){

    }
    //借阅图书
    public void borrowBook(){

    }

    //当前借阅模块,包括查询当前借阅图书、归还图书、确认归还图书三个功能
    //查询当前借阅图书
    public void searchBorrowed(){

    }
    //归还图书
    public void returnBook(){

    }
    //确认归还（管理员）
    public void returnConfirm(){

    }

}
