package com.aeon.library.service;

import com.aeon.library.model.Book;
import com.aeon.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterBook_Success() {
        Book book = new Book(1L, "978-3-16-148410-0", "The Great Book", "John Smith", false);

        when(bookRepository.findAll()).thenReturn(List.of());
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.registerBook(book);

        assertNotNull(result);
        assertEquals("The Great Book", result.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testRegisterBook_IsbnMismatch() {
        Book existingBook = new Book(1L, "978-3-16-148410-0", "The Great Book", "John Smith", false);
        Book newBook = new Book(2L, "978-3-16-148410-0", "Different Title", "Jane Doe", false);

        when(bookRepository.findAll()).thenReturn(List.of(existingBook));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bookService.registerBook(newBook));

        assertEquals("Title and author must match for books with the same ISBN.", exception.getMessage());
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book(1L, "978-3-16-148410-0", "The Great Book", "John Smith", false);
        Book book2 = new Book(2L, "978-1-23-456789-7", "Another Book", "Jane Doe", false);

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("The Great Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById_Found() {
        Book book = new Book(1L, "978-3-16-148410-0", "The Great Book", "John Smith", false);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("The Great Book", result.get().getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(999L);

        assertFalse(result.isPresent());
    }
}