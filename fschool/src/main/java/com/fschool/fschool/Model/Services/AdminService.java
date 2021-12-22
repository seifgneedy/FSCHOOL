package com.fschool.fschool.Model.Services;

import java.util.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Entities.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    UserRepository userRepository;
    CourseRepository courseRepository;

    @Autowired
    AdminService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public List<User> getStudents() {
        return userRepository.findByRole("student");
    }

    public List<User> getTeachers() {
        return userRepository.findByRole("teacher");
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public boolean addUser(User user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if (u.isPresent()){
            return false;
        }
        else {

            user.setPassword(hashPassword(user.getPassword()));
            userRepository.save(user);
            return true;
        }
    }

    public boolean changePassword(Long id, String password) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setPassword(hashPassword(password));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    public boolean addCourse(String code, String name) {
        Course course = new Course();
        course.setCode(code);
        course.setName(name);
        Optional<Course> c = courseRepository.findByCode(code);
        if(! c.isPresent()){
            courseRepository.save(course);
            return true;
        }
        else
            return false;
    }

    public Course getCourse(String code) {
        Optional<Course> course = courseRepository.findByCode(code);
        if (course.isPresent()) {
            return course.get();
        }
        return null;
    }

    public String addUserToCourse(Long userId, String courseCode, String role) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Course> course = courseRepository.findByCode(courseCode);
        if (user.isPresent() && course.isPresent()) {
            if(user.get().getRole().equals(role) && course.get().addUser(user.get()));
                return user.get().getFirstName() + " " + user.get().getLastName();
        }
        return null;
    }

    public boolean removeUserFromCourse(Long userId, String courseCode) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Course> course = courseRepository.findByCode(courseCode);
        if (user.isPresent() && course.isPresent()) {
            return course.get().removeUser(user.get());
        }
        return false;
    }

    public boolean deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;

    }

    public boolean deleteCourse(String code) {
        Optional<Course> course = courseRepository.findByCode(code);
        if (course.isPresent()) {
            courseRepository.delete(course.get());
            return true;
        }
        return false;
    }

    private String hashPassword(String password){
        return DigestUtils.sha256Hex(password);
    }


}
