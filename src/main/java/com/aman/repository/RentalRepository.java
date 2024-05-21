package com.aman.repository;

import com.aman.model.Rental;
import com.aman.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    int countByUserAndReturned(User user, boolean returned);
    Optional<Rental> findByBookIdAndUserAndReturned(Long bookId, User user, boolean returned);
}
