package com.aeon.library.service;

import com.aeon.library.model.Book;
import com.aeon.library.model.Borrower;
import com.aeon.library.repository.BookRepository;
import com.aeon.library.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aeon.library.exception.BorrowerNotFoundException;
import com.aeon.library.exception.BookNotFoundException;
import com.aeon.library.exception.BookAlreadyBorrowedException;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BorrowingService {
	
	private static final Logger logger = LoggerFactory.getLogger(BorrowingService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    // Borrow a book
    public String borrowBook(Long borrowerId, Long bookId) {
        logger.info("Attempting to borrow book with ID: {} by borrower ID: {}", bookId, borrowerId);

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> {
                    logger.error("Borrower with ID {} not found.", borrowerId);
                    return new BorrowerNotFoundException(borrowerId);
                });

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    logger.error("Book with ID {} not found.", bookId);
                    return new BookNotFoundException(bookId);
                });

        if (book.isBorrowed()) {
            logger.warn("Book with ID {} is already borrowed.", bookId);
            throw new BookAlreadyBorrowedException(bookId);
        }

        book.setBorrowed(true);
        bookRepository.save(book);
        logger.info("Book with ID: {} borrowed successfully by {}", bookId, borrower.getName());
        return "Book borrowed successfully by " + borrower.getName();
    }

    // Return a book
    public String returnBook(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isEmpty()) {
            return "Book not found.";
        }

        Book book = bookOpt.get();
        if (!book.isBorrowed()) {
            return "Book is not currently borrowed.";
        }

        book.setBorrowed(false);
        bookRepository.save(book);
        return "Book returned successfully.";
    }
}