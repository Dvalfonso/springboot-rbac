package com.rbacjava.repos;

import com.rbacjava.models.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String name);
    public boolean existsByEmail(String email);

    public Optional<User> findByEmail(String email);
}
