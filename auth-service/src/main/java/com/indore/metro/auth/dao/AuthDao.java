package com.indore.metro.auth.dao;

import com.indore.metro.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthDao extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
