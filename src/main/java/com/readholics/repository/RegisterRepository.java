package com.readholics.repository;

import com.readholics.model.Book;
import com.readholics.model.BookRegister;
import com.readholics.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RegisterRepository extends JpaRepository<BookRegister, Integer> {

    @Query("SELECT b.title AS title, b.author AS author, COUNT(br.id) AS borrowCount, (b.totalCopies - COUNT(br.id)) AS availableCopies " +
            "FROM BookRegister br " +
            "JOIN br.book b " +
            "JOIN Genre g ON b.genreId = g.id " +
            "WHERE g.genreName = :genreName " +
            "GROUP BY b.title, b.author, b.totalCopies " +
            "ORDER BY borrowCount DESC")
    List<Object[]> getTopBooksByGenreName(@Param("genreName") String genreName, Pageable pageable);
    BookRegister findByBookAndUserAndReturnDateIsNull(@Param("book") Book book, @Param("user") User user);
}