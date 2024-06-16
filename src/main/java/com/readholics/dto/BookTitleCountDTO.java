package com.readholics.dto;

public class BookTitleCountDTO {
    private String title;
    private String author;
    private long borrowCount;
    private long availableCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(long borrowCount) {
        this.borrowCount = borrowCount;
    }

    public long getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(long availableCount) {
        this.availableCount = availableCount;
    }

    public BookTitleCountDTO(String title, String author, long borrowCount, long availableCount) {
        this.title = title;
        this.author = author;
        this.borrowCount = borrowCount;
        this.availableCount = availableCount;
    }

    public BookTitleCountDTO() {
    }
}
