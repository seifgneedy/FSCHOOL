package com.fschool.fschool.Controllers;

import java.util.*;


import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Services.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = { "http://localhost:8080" },
            allowCredentials = "true")
@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(path="student/courses")
    public List<Course> getCourses(@CookieValue("id") String id){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        return studentService.getCourses(Long.valueOf(id));
    }

    @GetMapping(path="student/{courseCode}")
    public List<Post> getPosts(@CookieValue("id") String id,
                                @PathVariable("courseCode") String courseCode, 
                                @RequestParam String PostType){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        // Check if user in course --> done in service class
        return studentService.getPostsByType(courseCode, PostType, id);
    }
    /*@GetMapping(path="student/")
    public List<Comment> getCourses(@CookieValue(value = "id", defaultValue = "") String id,
                                @RequestParam String courseCode){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        // Check if user in course
        return studentService.getPosts(courseCode);
    }*/
    /*@GetMapping(path = "student/course/{code}")
    public Course getCourse(@PathVariable String code) {
        // Check if user in course or not
        /////////////
        //////////////////
        return studentService.getCourse(code);
    }*/
}
