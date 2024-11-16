package com.aeon.library.service;

import com.aeon.library.model.Borrower;
import com.aeon.library.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowerServiceTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerService borrowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterBorrower() {
        Borrower borrower = new Borrower(1L, "Alice", "alice@example.com");

        when(borrowerRepository.save(borrower)).thenReturn(borrower);

        Borrower result = borrowerService.registerBorrower(borrower);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(borrowerRepository, times(1)).save(borrower);
    }

    @Test
    void testGetAllBorrowers() {
        Borrower borrower1 = new Borrower(1L, "Alice", "alice@example.com");
        Borrower borrower2 = new Borrower(2L, "Bob", "bob@example.com");

        when(borrowerRepository.findAll()).thenReturn(List.of(borrower1, borrower2));

        List<Borrower> result = borrowerService.getAllBorrowers();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        verify(borrowerRepository, times(1)).findAll();
    }

    @Test
    void testGetBorrowerById_Found() {
        Borrower borrower = new Borrower(1L, "Alice", "alice@example.com");

        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));

        Optional<Borrower> result = borrowerService.getBorrowerById(1L);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    void testGetBorrowerById_NotFound() {
        when(borrowerRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Borrower> result = borrowerService.getBorrowerById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteBorrower() {
        borrowerService.deleteBorrower(1L);

        verify(borrowerRepository, times(1)).deleteById(1L);
    }
}