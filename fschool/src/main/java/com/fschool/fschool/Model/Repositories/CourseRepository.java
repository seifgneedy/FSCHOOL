package com.fschool.fschool.Model.Repositories;

import com.fschool.fschool.Model.Entities.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByCode(String Code);
    Course findByName(String Name);
}
