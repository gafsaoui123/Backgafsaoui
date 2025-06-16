package com.app.app.dao;

import com.app.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    long countByLastLoginDateGreaterThan(LocalDateTime date);
}