package com.fschool.fschool.Controllers;

import java.util.*;


import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Services.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:8080" },
            allowCredentials = "true")
@RestController
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    
    @GetMapping(path="teacher/courses")
    public List<Course> getCourses(@CookieValue("id") String id){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        return teacherService.getCourses(Long.valueOf(id));
    }

    @GetMapping(path="teacher/{courseCode}")
    public List<Post> getPosts(@CookieValue("id") String id,
                                @RequestParam String courseCode,
                                @RequestParam String postType){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        // Check if user in course
        return teacherService.getPostsByType(courseCode, postType, id);
    }
















    /*@GetMapping(path = "teacher/course/{code}")
    public Course getCourse(@PathVariable String code) {
        // Check if user in course or not
        /////////////
        //////////////////
        return teacherService.getCourse(code);
    }*/
}
