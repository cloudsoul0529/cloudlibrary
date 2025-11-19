package org.seventhgroup.pojo;

/**
 * @author oxygen
 * @time 2025.11.17
 * */

public class Record {
    private Long id;
    private String bookName;
    private String bookIsbn;
    private String borrower;
    private String borrowTime;
    private String remandTime;

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookname(String bookName) {
        this.bookName = bookName;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public void setRemandTime(String remandTime) {
        this.remandTime = remandTime;
    }

    public Long getId() {
        return id;
    }

    public String getBookname() {
        return bookName;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getBorrower() {
        return borrower;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public String getRemandTime() {
        return remandTime;
    }
}
