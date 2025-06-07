package com.repository;

import com.entities.VocabArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabAreaRepository extends JpaRepository<VocabArea, Long> {
    List<VocabArea> findByAreaNameContainingIgnoreCase(String name);
    List<VocabArea> findByAreaId(Long areaId);
//    boolean existsByName(String name);
} 