package com.vjay.libararymanagement.model;

public class BookingHistory {
    private String bookName;
    private String authorName;
    private String studentName;
    private String bookedAt;
    private String returnedAt;
    private BookReturnStatus bookReturnStatus;

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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(String bookedAt) {
        this.bookedAt = bookedAt;
    }

    public String getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(String returnedAt) {
        this.returnedAt = returnedAt;
    }

    public BookReturnStatus getBookReturnStatus() {
        return bookReturnStatus;
    }

    public void setBookReturnStatus(BookReturnStatus bookReturnStatus) {
        this.bookReturnStatus = bookReturnStatus;
    }
}
