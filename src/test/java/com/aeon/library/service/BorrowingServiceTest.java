package com.aeon.library.service;

import com.aeon.library.exception.BookAlreadyBorrowedException;
import com.aeon.library.exception.BookNotFoundException;
import com.aeon.library.exception.BorrowerNotFoundException;
import com.aeon.library.model.Book;
import com.aeon.library.model.Borrower;
import com.aeon.library.repository.BookRepository;
import com.aeon.library.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook_Success() {
        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("Alice");
        borrower.setEmail("alice@example.com");

        Book book = new Book();
        book.setId(1L);
        book.setIsbn("978-3-16-148410-0");
        book.setTitle("The Great Book");
        book.setAuthor("John Smith");
        book.setBorrowed(false);

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        String result = borrowingService.borrowBook(1L, 1L);

        assertEquals("Book borrowed successfully by Alice", result);
        assertTrue(book.isBorrowed());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testBorrowBook_BookAlreadyBorrowed() {
        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("Alice");
        borrower.setEmail("alice@example.com");

        Book book = new Book();
        book.setId(1L);
        book.setIsbn("978-3-16-148410-0");
        book.setTitle("The Great Book");
        book.setAuthor("John Smith");
        book.setBorrowed(true);

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(BookAlreadyBorrowedException.class, () -> borrowingService.borrowBook(1L, 1L));
        verify(bookRepository, never()).save(book);
    }

    @Test
    void testBorrowBook_BorrowerNotFound() {
        when(borrowerRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BorrowerNotFoundException.class, () -> borrowingService.borrowBook(999L, 1L));
    }

    @Test
    void testBorrowBook_BookNotFound() {
        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("Alice");
        borrower.setEmail("alice@example.com");

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> borrowingService.borrowBook(1L, 999L));
    }
}