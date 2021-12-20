package com.fschool.fschool.Model.Repositories;

import java.util.Optional;

import com.fschool.fschool.Model.Entities.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByCode(String Code);
    Optional<Course> findByName(String Name);
}
