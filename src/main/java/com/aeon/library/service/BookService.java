package com.aeon.library.service;

import com.aeon.library.model.Book;

import com.aeon.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.aeon.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Register a new book with ISBN validation
    public Book registerBook(Book book) {
        List<Book> existingBooks = bookRepository.findAll().stream()
                .filter(b -> b.getIsbn().equals(book.getIsbn()))
                .collect(Collectors.toList());

        if (!existingBooks.isEmpty()) {
            boolean mismatch = existingBooks.stream()
                    .anyMatch(b -> !b.getTitle().equals(book.getTitle()) || !b.getAuthor().equals(book.getAuthor()));
            if (mismatch) {
                throw new IllegalArgumentException("Title and author must match for books with the same ISBN.");
            }
        }

        return bookRepository.save(book);
    }

    // Get a list of all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Delete a book by ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}