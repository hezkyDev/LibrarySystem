package com.aeon.library.exception;

public class BorrowerNotFoundException extends RuntimeException {
    public BorrowerNotFoundException(Long id) {
        super("Borrower with ID " + id + " not found.");
    }
}