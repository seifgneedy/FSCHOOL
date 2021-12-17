package com.fschool.fschool.Model;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findByRole(String role);
    User findByEmail(String email);
}