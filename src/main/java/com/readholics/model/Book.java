package com.readholics.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @NotBlank(message = "Title is mandatory!")
    private String title;

    @Getter
    @Setter
    @NotBlank(message = "Author is mandatory!")
    private String author;

    @Getter
    @Setter
    @NotNull(message = "Genre Id is mandatory!")
    private int genreId;

    @Setter
    @Getter
    @Min(value=1,message="At least one copy required!")
    private int totalCopies;

    @Getter
    @Setter
    @Min(value=0,message="Available copies can't be negative!")
    private int availableCopies;

    @Getter
    @Setter
    @Min(value=0,message="Borrow Count can't be negative!")
    private int borrowCount;

    public Book() {
    }

    public Book(int id, String title, String author, int genreId) {
        this.title = title;
        this.author = author;
        this.genreId = genreId;
    }

    public int getBookId() {
        return id;
    }

    @Min(value = 0, message = "Available copies can't be negative!")
    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(@Min(value = 0, message = "Available copies can't be negative!") int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
