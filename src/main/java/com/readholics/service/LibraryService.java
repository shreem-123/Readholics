package com.readholics.service;

import com.readholics.dto.BookTitleCountDTO;
import com.readholics.model.Book;
import com.readholics.model.BookRegister;
import com.readholics.model.Genre;
import com.readholics.model.User;
import com.readholics.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.readholics.repository.UserRepository;
import com.readholics.repository.BookRepository;
import com.readholics.repository.GenreRepository;


import java.util.*;

@Service
public class LibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RegisterRepository registerRepository;

    // Book-related methods
    public boolean borrowBook(long bookId, long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if ("inactive".equalsIgnoreCase(user.getStatus())) {
            throw new IllegalStateException("Inactive user");
        }

        // Create a new BookRegister instance
        BookRegister bookRegister = new BookRegister();
        bookRegister.setBook(book);
        bookRegister.setUser(user);
        bookRegister.setBorrowDate(new Date());

//        // Calculate returnDate based on borrowDate + 2 months
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(bookRegister.getBorrowDate());
//        calendar.add(Calendar.MONTH, 2);
        bookRegister.setReturnDate(null);


        // Save BookRegister to database
        registerRepository.save(bookRegister);

        // Update book information
        synchronized (this) { // To handle concurrent borrow requests
            if (book.getAvailableCopies() <= 0) {
                throw new IllegalStateException("No copies available to borrow.");
            }
            book.setBorrowCount(book.getBorrowCount() + 1);
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        }
        return true;
    }

    public void returnBook(long bookId, long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        synchronized (this) { // To handle concurrent return requests
            if (book.getAvailableCopies() < book.getTotalCopies()) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                bookRepository.save(book);
            } else {
                throw new IllegalStateException("Cannot return more copies than total available.");
            }
        }
    }



    public List<BookTitleCountDTO> getTopBooksByGenreName(String genreName, int recCount) {
        Pageable pageable = PageRequest.of(0, recCount);
        List<Object[]> resultList = registerRepository.getTopBooksByGenreName(genreName,pageable);
        List<BookTitleCountDTO> dtos = new ArrayList<>(recCount);

        for (Object[] row : resultList) {
            String title = (String) row[0];
            String authorName = (String) row[1];
            long borrowCount = (long) row[2];
            long availableCount = (long) row[3];

            BookTitleCountDTO dto = new BookTitleCountDTO(title, authorName, borrowCount, availableCount);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<Book> saveBook(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    // User-related methods
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public List<User> saveUser(List<User> users) {
        return userRepository.saveAll(users);
    }

    // Genre-related methods
    public Genre findGenreById(Long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        return genre.orElse(null);
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}