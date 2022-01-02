package com.fschool.fschool.Model.Services;

import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends UserService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;
}
