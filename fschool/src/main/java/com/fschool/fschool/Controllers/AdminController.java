package com.fschool.fschool.Controllers;
import java.time.LocalDate;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    @PostMapping("/admin/add/user")
    public boolean addUser (@RequestParam String email,
                        @RequestParam String firstName, @RequestParam String lastName,
                        @RequestParam char sex, @RequestParam LocalDate birthDate,
                        @RequestParam String password, @RequestParam String role){
    
        return adminService.addUser(email, firstName, lastName, sex, birthDate, password, role);    
    }
    @PostMapping("/admin/changePassword")
    public boolean updateUser (@RequestParam Long id, @RequestParam String password){
        return adminService.changePassword(id, password);    
    }
    @PostMapping("/admin/add/course")
    public boolean addCourse (@RequestParam String code, @RequestParam String name){
        return adminService.addCourse(code, name);
    }

    @GetMapping(path = "admin/course")
    public Course getCourse(@RequestParam String code) {
        Course course = adminService.getCourse(code);
        if(course == null){
            //to do : handle to course found by this ID;
        }
        return course;
    }

    @GetMapping(path = "admin/addToCourse")
    public boolean addUserToCourse(@RequestParam Long userId, @RequestParam String courseCode) {
        return adminService.addUserToCourse(userId, courseCode);
    }

    @DeleteMapping(path = "admin/removeFromCourse")
    public boolean removeUserFromCourse(@RequestParam Long userId, @RequestParam String courseCode) {
        return adminService.removeUserFromCourse(userId, courseCode);
    }

    @DeleteMapping(path = "delete/user")
    public boolean deleteUser(@RequestParam Long id){
        return adminService.deleteUser(id);
    }

    @DeleteMapping(path = "delete/course")
    public boolean deleteUser(@RequestParam String code){
        return adminService.deleteCourse(code);
    }

    



}

