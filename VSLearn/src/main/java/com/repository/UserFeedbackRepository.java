package com.repository;

import com.entities.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Long> {
//    Optional<UserFeedback> findByName(String name);
//    boolean existsByName(String name);
}