package com.fschool.fschool.Model.Services;

import java.util.*;

import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class UserService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;

    public Set<Course> getCourses(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get().getCourses();
        }
        // very bad practice
        return null;
        
    }
}
