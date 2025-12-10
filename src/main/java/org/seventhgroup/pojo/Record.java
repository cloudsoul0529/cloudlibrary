package org.seventhgroup.pojo;

import java.io.Serializable;

/**
 * 借阅记录
 *
 * @author sooooya838
 */
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    //记录ID
    private Integer id;
    //书名
    private String bookname;
    //ISBN
    private String bookisbn;
    //借阅人
    private String borrower;
    //借阅时间
    private String borrowTime;
    //归还时间
    private String remandTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getBookname() { return bookname; }
    public void setBookname(String bookname) { this.bookname = bookname; }
    public String getBookisbn() { return bookisbn; }
    public void setBookisbn(String bookisbn) { this.bookisbn = bookisbn; }
    public String getBorrower() { return borrower; }
    public void setBorrower(String borrower) { this.borrower = borrower; }
    public String getBorrowTime() { return borrowTime; }
    public void setBorrowTime(String borrowTime) { this.borrowTime = borrowTime; }
    public String getRemandTime() { return remandTime; }
    public void setRemandTime(String remandTime) { this.remandTime = remandTime; }
}