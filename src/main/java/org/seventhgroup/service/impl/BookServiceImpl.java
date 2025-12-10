package org.seventhgroup.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.seventhgroup.dao.BookMapper;
import org.seventhgroup.dto.PageResult;
import org.seventhgroup.pojo.Book;
import org.seventhgroup.pojo.Record;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.BookService;
import org.seventhgroup.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DeadSoup Agony123466
 * @time 2025/11/19 17:06
 */

@Service
@Transactional
public class BookServiceImpl implements BookService {
    // 常量定义（消除魔法值，提升可读性）
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final String ROLE_ADMIN = "ADMIN";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RecordService recordService;

    /**
     * 查询最新上架图书 - 仅优化风格，保留原固定分页逻辑
     */
    @Override
    public PageResult selectNewBooks() {
        // 设置分页参数（保留原逻辑：第1页，每页5条）
        PageHelper.startPage(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
        Page<Book> page = bookMapper.selectNewBooks();
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID查询图书 - 优化空值校验、代码注释
     */
    @Override
    public Book findById(String id) {
        // 空值校验
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return bookMapper.findById(id);
    }

    /**
     * 借阅图书 - 优化注释、空值校验
     */
    @Override
    public Integer borrowBook(Book book) {
        // 校验图书ID
        if (book.getId() == null) {
            return 0;
        }
        // 查询完整图书信息
        Book targetBook = this.findById(book.getId().toString());
        if (targetBook == null) {
            return 0;
        }
        // 设置借阅时间和状态
        book.setBorrowTime(DATE_FORMAT.format(new Date()));
        book.setStatus(Book.BORROWED);
        // 回填图书基础信息
        book.setPrice(targetBook.getPrice());
        book.setUploadTime(targetBook.getUploadTime());
        return bookMapper.editBook(book);
    }

    /**
     * @param book 封装查询条件的对象
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize)
    {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page=bookMapper.searchBooks(book);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增图书
     * @param book 页面提交的新增图书信息
     */
    @Override
    public Integer addBook(Book book)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置新增图书的上架时间
        book.setUploadTime(dateFormat.format(new Date()));
        return  bookMapper.addBook(book);
    }

    /**
     * 编辑图书信息
     * @param book 图书信息
     */
    @Override
    public Integer editBook(Book book)
    {

        return bookMapper.editBook(book);
    }

    /**
     * 查询用户当前借阅的图书
     * @param book 封装查询条件的对象
     * @param user 当前登录用户
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page;
        //将当前登录的用户放入查询条件中
        book.setBorrower(user.getName());
        //如果是管理员，查询自己借阅但未归还的图书和所有待确认归还的图书
        if("ADMIN".equals(user.getRole())){
            page= bookMapper.selectBorrowed(book);
        }else {
            //如果是普通用户，查询自己借阅但未归还的图书
            page= bookMapper.selectMyBorrowed(book);
        }
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 归还图书
     * @param id 归还的图书的id
     * @param user 归还的人员，也就是当前图书的借阅者
     */
    @Override
    public boolean returnBook(String id,User user) {
        //根据图书id查询出图书的完整信息
        Book book = this.findById(id);
        //再次核验当前登录人员和图书借阅者是不是同一个人
        boolean rb=book.getBorrower().equals(user.getName());
        //如果是同一个人，允许归还
        if(rb){
            //将图书借阅状态修改为归还中
            book.setStatus(Book.RETURNING);
            bookMapper.editBook(book);
        }
        return rb;
    }
    /**
     * 归还确认
     * @param id 待归还确认的图书id
     */
    @Override
    public Integer returnConfirm(String id) {
        //根据图书id查询图书的完整信息
        Book book = this.findById(id);
        //根据归还确认的图书信息，设置借阅记录
        Record record = this.setRecord(book);
        //将图书的借阅状态修改为可借阅
        book.setStatus(Book.AVAILABLE);
        //清除当前图书的借阅人信息
        book.setBorrower("");
        //清除当前图书的借阅时间信息
        book.setBorrowTime("");
        //清除当亲图书的预计归还时间信息
        book.setReturnTime("");
        Integer count= bookMapper.editBook(book);
        //如果归还确认成功，则新增借阅记录
        if(count==1){
            return  recordService.addRecord(record);
        }
        return 0;
    }
    /**
     * 根据图书信息设置借阅记录的信息
     * @param book 借阅的图书信息
     */
    private Record setRecord(Book book){
        Record record=new Record();
        //设置借阅记录的图书名称
        record.setBookname(book.getName());
        //设置借阅记录的图书isbn
        record.setBookisbn(book.getIsbn());
        //设置借阅记录的借阅人
        record.setBorrower(book.getBorrower());
        //设置借阅记录的借阅时间
        record.setBorrowTime(book.getBorrowTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置图书归还确认的当天为图书归还时间
        record.setRemandTime(dateFormat.format(new Date()));
        return record;
    }
}