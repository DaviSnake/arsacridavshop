package com.aracridav.aracridavback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aracridav.aracridavback.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Boolean existsByFullName(String fullName);

    @Query("SELECT MAX(u.id) FROM User u")
    Long findMaxId();
}
