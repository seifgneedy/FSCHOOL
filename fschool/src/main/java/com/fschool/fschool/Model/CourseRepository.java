package com.fschool.fschool.Model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByCode(String Code);
    Course findByName(String Name);


}
