package com.fschool.fschool.Model.Services;

import java.util.*;

import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public List<Course> getCourses(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ArrayList<Course>(user.get().getCourses());
        }
        return null;
    }

    public Course getCourse(String code) {
        Optional<Course> course = courseRepository.findByCode(code);
        if (course.isPresent()) {
            return course.get();
        }
        return null;
    }
}
