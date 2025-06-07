package com.repository;

import com.entities.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTopicRepository extends JpaRepository<SubTopic, Long> {
    List<SubTopic> findByTopicId(Long topicId);
    List<SubTopic> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
} 