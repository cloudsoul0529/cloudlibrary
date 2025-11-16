package org.seventhgroup.pojo;

import org.seventhgroup.pojo.enums.Status;

/**
 * @author oxygen
 * @time 2025.11.16
 **/
public class Book {

    private String name;
    private String isbn;
    private String press;
    private String author;
    private Integer pagination;
    private Double price;
    private String uploadTime;
    private Status status;


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

    public void setStatus(Status status) {
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

    public Status getStatus() {
        return status;
    }

}
