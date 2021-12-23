package com.fschool.fschool.Controllers;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = { "http://localhost:8080" })
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
    @GetMapping(path="/admin/user")
    public User getUser(@RequestParam Long id){
        return adminService.getUser(id);
    }
    @GetMapping(path = "/admin/students")
    public List<User> getStudents(){
        return adminService.getStudents();
    }
    @GetMapping(path = "/admin/teachers")
    public List<User> getTeachers(){
        return adminService.getTeachers();
    }

    @PostMapping("/admin/user")
    public Long addUser (@RequestBody User user){
        return adminService.addUser(user);    
    }
    @PostMapping("/admin/changePassword")
    public boolean updateUser (@RequestParam Long id, @RequestParam String password){
        return adminService.changePassword(id, password);    
    }
    @PostMapping("/admin/course")
    public boolean addCourse (@RequestParam String code, @RequestParam String name){
        return adminService.addCourse(code, name);
    }

    @GetMapping(path = "admin/course/{code}")
    public Course getCourse(@PathVariable String code) {
        Course course = adminService.getCourse(code);
        return course;
    }

    @PostMapping(path = "admin/addToCourse")
    public String addUserToCourse(@RequestParam Long userId, @RequestParam String courseCode, @RequestParam String role) {
        return adminService.addUserToCourse(userId, courseCode, role);
    }

    @DeleteMapping(path = "admin/removeFromCourse")
    public boolean removeUserFromCourse(@RequestParam Long userId, @RequestParam String courseCode) {
        return adminService.removeUserFromCourse(userId, courseCode);
    }

    @DeleteMapping(path = "admin/user")
    public boolean deleteUser(@RequestParam Long id){
        System.out.println(id);
        return adminService.deleteUser(id);
    }

    @DeleteMapping(path = "admin/course")
    public boolean deleteUser(@RequestParam String code){
        return adminService.deleteCourse(code);
    }

    @GetMapping(path = "admin/courseMembers")
    public List<User> getCourseMembers(@RequestParam String courseCode, @RequestParam String role){
        return adminService.getCourseMembers(courseCode, role);
    }

    @PutMapping(path = "admin/updateCourseName")
    public boolean updateCourseName (@RequestParam String courseCode, @RequestParam String courseName){
        return adminService.updateCourseName(courseCode, courseName);
    }

}

