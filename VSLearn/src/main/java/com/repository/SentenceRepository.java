package com.repository;

import com.entities.Sentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long> {
//    List<QuestionType> findByNameContainingIgnoreCase(String name);
//    boolean existsByName(String name);
} 