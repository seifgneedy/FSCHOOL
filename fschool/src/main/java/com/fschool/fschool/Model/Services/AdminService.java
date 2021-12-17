package com.fschool.fschool.Model.Services;
import java.util.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    
    public List<User> getStudents(){
        return userRepository.findByRole("Student");
    }
    public List<User> getTeachers(){
        return userRepository.findByRole("Teacher");
    }
    public List<Course> getCourses(){
        return courseRepository.findAll();
    }
}
