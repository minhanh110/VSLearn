package com.repository;

import com.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
//    List<Question> findBySubTopicId(Long subTopicId);
//    List<Question> findByQuestionTypeId(Long questionTypeId);
//    List<Question> findByQuestionContainingIgnoreCase(String question);
} 