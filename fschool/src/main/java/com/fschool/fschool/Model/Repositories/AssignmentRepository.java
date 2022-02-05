package com.fschool.fschool.Model.Repositories;

import com.fschool.fschool.Model.Entities.Assignment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment,Long> {
    
}
