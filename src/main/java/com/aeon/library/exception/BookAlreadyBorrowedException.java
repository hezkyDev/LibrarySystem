package com.aeon.library.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(Long id) {
        super("Book with ID " + id + " is already borrowed.");
    }
}