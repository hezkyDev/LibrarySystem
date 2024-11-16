package com.aeon.library.service;

import com.aeon.library.exception.BorrowerNotFoundException;
import com.aeon.library.model.Borrower;
import com.aeon.library.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

 // Register a new borrower with email validation
    public Borrower registerBorrower(Borrower borrower) {
        Optional<Borrower> existingBorrower = borrowerRepository.findByEmail(borrower.getEmail());
        if (existingBorrower.isPresent()) {
            throw new IllegalArgumentException("Borrower with email " + borrower.getEmail() + " already exists.");
        }
        return borrowerRepository.save(borrower);
    }

    // Get a list of all borrowers
    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    // Get borrower by ID
    public Optional<Borrower> getBorrowerById(Long id) {
        return borrowerRepository.findById(id);
    }

    // Delete a borrower by ID
    public void deleteBorrower(Long id) {
        borrowerRepository.deleteById(id);
    }
}