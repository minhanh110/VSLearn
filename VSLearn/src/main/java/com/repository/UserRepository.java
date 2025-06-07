package com.repository;

import com.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
//    List<User> findByRoleIs(Integer roleId);
    
    List<User> findByIsActive(Boolean isActive);
    
//    List<User> findByCreatedAtAfter(LocalDateTime date);
    
//    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<User> findByCreatedBy(Integer createdBy);
    
    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email);
    
    @Modifying
    @Query("UPDATE User u SET u.deletedAt = :deletedAt, u.deletedBy = :deletedBy WHERE u.id = :userId")
    void softDelete(Long userId, LocalDateTime deletedAt, Integer deletedBy);
    
    @Modifying
    @Query("UPDATE User u SET u.deletedAt = NULL, u.deletedBy = NULL WHERE u.id = :userId")
    void restore(Long userId);
    
    @Modifying
    @Query("UPDATE User u SET u.isActive = :isActive, u.updatedAt = :updatedAt, u.updatedBy = :updatedBy WHERE u.id = :userId")
    void updateActiveStatus(Long userId, Boolean isActive, LocalDateTime updatedAt, Integer updatedBy);
    
    @Modifying
    @Query("UPDATE User u SET u.role.id = :roleId, u.updatedAt = :updatedAt, u.updatedBy = :updatedBy WHERE u.id = :userId")
    void updateRole(Long userId, Integer roleId, LocalDateTime updatedAt, Integer updatedBy);
    
    @Modifying
    @Query("UPDATE User u SET u.profilePicture = :profilePicture, u.updatedAt = :updatedAt, u.updatedBy = :updatedBy WHERE u.id = :userId")
    void updateProfilePicture(Long userId, String profilePicture, LocalDateTime updatedAt, Integer updatedBy);
    
    @Query("SELECT u FROM User u WHERE u.updatedAt < :date AND u.deletedAt IS NULL")
    List<User> findInactiveUsers(LocalDateTime date);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.role.id = :roleId AND u.deletedAt IS NULL")
    Long countByRole(Integer roleId);
    
//    List<User> findByRoleIdAndIsActive(Integer roleId, Boolean isActive);
}
