package com.aeon.library.repository;

import com.aeon.library.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Optional<Borrower> findByEmail(String email);
}