package com.repository;

import com.entities.Vocab;
import com.entities.VocabArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface VocabAreaRepository extends JpaRepository<VocabArea, Long> {
    List<VocabArea> findByVocab(@NotNull Vocab vocab);
    List<VocabArea> findByAreaId(Long areaId);
//    boolean existsByName(String name);
} 