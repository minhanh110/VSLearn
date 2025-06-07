package com.repository;

import com.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByPackageId(Long packageId);
    List<Transaction> findByUserIdAndPackageId(Long userId, Long packageId);
} 