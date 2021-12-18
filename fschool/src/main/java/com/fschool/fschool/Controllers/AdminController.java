package com.fschool.fschool.Controllers;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    // admin password : turkish coffee
    AdminService adminService;
    @Autowired
    AdminController(AdminService adminService){
        this.adminService=adminService;
    }
    @GetMapping(path="/admin/courses")
    public List<Course> getCourses(){
        return adminService.getCourses();
    }
    @GetMapping(path = "/admin/students")
    public List<User> getStudents(){
        return adminService.getStudents();
    }
    @GetMapping(path = "/admin/teachers")
    public List<User> getTeachers(){
        return adminService.getTeachers();
    }
}
