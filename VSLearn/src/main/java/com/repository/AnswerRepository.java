package com.repository;

import com.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUserId(Long userId);
    List<Answer> findByTestQuestionId(Long testQuestionId);
    List<Answer> findByUserIdAndTestQuestionId(Long userId, Long testQuestionId);
} 