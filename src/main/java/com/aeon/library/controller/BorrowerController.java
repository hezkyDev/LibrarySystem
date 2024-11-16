package com.aeon.library.controller;

import com.aeon.library.model.Borrower;
import com.aeon.library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    // Register a new borrower
    @PostMapping
    public ResponseEntity<?> registerBorrower(@RequestBody Borrower borrower) {
        try {
            Borrower newBorrower = borrowerService.registerBorrower(borrower);
            String message = "Borrower registered successfully: " + newBorrower.getName();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Get all borrowers
    @GetMapping
    public ResponseEntity<List<Borrower>> getAllBorrowers() {
        List<Borrower> borrowers = borrowerService.getAllBorrowers();
        return new ResponseEntity<>(borrowers, HttpStatus.OK);
    }

    // Get a borrower by ID
    @GetMapping("/{id}")
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable Long id) {
        Optional<Borrower> borrower = borrowerService.getBorrowerById(id);
        return borrower.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete a borrower by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}