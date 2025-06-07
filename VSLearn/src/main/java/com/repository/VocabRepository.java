package com.repository;

import com.entities.Vocab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabRepository extends JpaRepository<Vocab, Long> {
    List<Vocab> findByVocabAreaId(Long vocabAreaId);
    List<Vocab> findByWordContainingIgnoreCase(String word);
    boolean existsByWord(String word);
} 