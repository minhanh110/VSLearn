package com.repository;

import com.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
} 