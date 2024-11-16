package com.aeon.library.controller;

import com.aeon.library.model.Book;
import com.aeon.library.service.BookService;
import com.aeon.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowingService borrowingService;

    // Register a new book with validation
    @PostMapping
    public ResponseEntity<String> registerBook(@RequestBody Book book) {
        try {
            Book newBook = bookService.registerBook(book);
            return new ResponseEntity<>("Book registered successfully: " + newBook.getTitle(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Borrow a book
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Long borrowerId, @RequestParam Long bookId) {
        try {
            String response = borrowingService.borrowBook(borrowerId, bookId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Return a book
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam Long bookId) {
        try {
            String response = borrowingService.returnBook(bookId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}