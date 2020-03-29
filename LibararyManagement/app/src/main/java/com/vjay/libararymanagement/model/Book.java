package com.vjay.libararymanagement.model;

import java.io.Serializable;

public class Book implements Serializable {
    private String bookName;
    private String authorName;
    private String description;
    private String bookCount;
    private int booksAvailableForStudent;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    public void availOneUnit(){
        this.booksAvailableForStudent -= 1;
    }

    public int getBooksAvailableForStudent() {
        return booksAvailableForStudent;
    }

    public void setBooksAvailableForStudent(int booksAvailableForStudent) {
        this.booksAvailableForStudent = booksAvailableForStudent;
    }
}
