package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findByNameContainingIgnoreCase(String name);
//    List<Package> findByPriceLessThanEqual(Double price);
    boolean existsByName(String name);
} 