package org.seventhgroup.pojo;

import org.seventhgroup.pojo.enums.BookStatus;

/**
 * @author oxygen
 * @time 2025.11.17
 **/
public class Book {

    private String name;
    private String isbn;
    private String press;
    private String author;
    private Integer pagination;
    private Double price;
    private String uploadTime;
    private BookStatus status;


    public void setName(String name) {
        this.name = name;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPagination(Integer pagination) {
        this.pagination = pagination;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPress() {
        return press;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPagination() {
        return pagination;
    }

    public Double getPrice() {
        return price;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public BookStatus getStatus() {
        return status;
    }

}
