package com.fschool.fschool.Controllers;

import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Services.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = { "http://localhost:8080" })
@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(path="student/courses")
    public List<Course> getCourses(){
        // Getting the user id via cookies or whatever
        ////////
        //////////
        ///////
        return studentService.getCourses(0L/*The id is here.............*/);
    }
    @GetMapping(path = "student/course/{code}")
    public Course getCourse(@PathVariable String code) {
        // Check if user in course or not
        /////////////
        //////////////////
        return studentService.getCourse(code);
    }
}
