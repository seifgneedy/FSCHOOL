package com.fschool.fschool.Model.Repositories;

import java.util.*;

import com.fschool.fschool.Model.Entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findByRole(String role);
    User findByEmail(String email);
}