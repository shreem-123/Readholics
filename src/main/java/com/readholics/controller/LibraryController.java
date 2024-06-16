package com.readholics.controller;

import com.readholics.dto.BookTitleCountDTO;
import com.readholics.model.Genre;
import com.readholics.model.User;
import com.readholics.service.LibraryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.readholics.model.Book;

import java.util.List;

@RestController
@RequestMapping("/library")
@Validated
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Endpoint to test if the service is running
    @GetMapping("/test")
    public String testService() {
        return "Service is running!";
    }

    // Book-related endpoints
    @PostMapping("/books/borrow")
    public boolean borrowBook(@RequestParam @Min(1) long bookId, @RequestParam @Min(1) long userId) {
        return libraryService.borrowBook(bookId, userId);
    }

    @PostMapping("/books/return")
    public void returnBook(@RequestParam @Min(1) long bookId, @RequestParam @Min(1) long userId) {
        libraryService.returnBook(bookId, userId);
    }

    @GetMapping("/books/topRecommendations")
    public List<BookTitleCountDTO> getTopBooksPerGenre(@RequestParam @NotBlank String genreName, @RequestParam @Min(1) int recCount) {
        return libraryService.getTopBooksByGenreName(genreName,recCount);
    }

    @PostMapping("/books/add")
    public List<Book> addBook(@Valid @RequestBody List<Book> books) {
        return libraryService.saveBook(books);
    }

    // User-related endpoints
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable @Min(1) Long id) {
        return libraryService.findUserById(id);
    }

    @PostMapping("/users/add")
    public List<User> addUser(@Valid @RequestBody List<User> users) {
        return libraryService.saveUser(users);
    }

    // Genre-related endpoints
    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable @Min(1) Long id) {
        return libraryService.findGenreById(id);
    }

    @PostMapping("/genres/add")
    public Genre addGenre(@Valid @RequestBody Genre genre) {
        return libraryService.saveGenre(genre);
    }
}
