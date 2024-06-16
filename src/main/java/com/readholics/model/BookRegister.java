package com.readholics.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="Book_Register")
public class BookRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="book_id", foreignKey = @ForeignKey(name="bookId"))
    @Valid
    private Book book;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name="userId"))
    @Valid
    private User user;

    @Getter
    @Setter
    @NotNull(message ="Borrow Date is mandatory!")
    private Date borrowDate;

    @Setter
    @Getter
    private Date returnDate;

    public int getRecordId() {
        return id;
    }

    public BookRegister(int id, Book book, User user, Date borrowDate, Date returnDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        if (returnDate != null) {
            this.returnDate = returnDate;
        } else {
            // Calculate returnDate based on borrowDate + 2 months
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowDate);
            calendar.add(Calendar.MONTH, 2);
            this.returnDate = calendar.getTime();
        }
    }

    public BookRegister() {
    }

}
