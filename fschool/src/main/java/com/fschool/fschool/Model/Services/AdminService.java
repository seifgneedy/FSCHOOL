package com.fschool.fschool.Model.Services;

import java.util.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Entities.*;

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
        return userRepository.findByRole("Student");
    }

    public List<User> getTeachers() {
        return userRepository.findByRole("Teacher");
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public boolean addUser(String email, String firstName,
                        String lastName, Char sex, LocalDate birthDate,
                        String password, String role) {
        if (!(role.equals("student") || role.equals("teacher") || role.equals("admin")))
            return false;
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setSex(sex);
        user.setBirthDate(birthDate);
        user.setHashedPassword(DigestUtils.sha256Hex(password));
        user.setRole(role);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Exception when adding user");
            return false;
        }

    }

    public boolean changePassword(Long id, String password){
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){
            user.get().setHashedPassword(DigestUtils.sha256Hex(password));
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    public boolean addCourse(String code, String name){
        Course course = new Course();
        course.setCode(code);
        course.setName(name);
        try {
            courseRepository.save(course);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Exception when adding course");
            return false;
        }
    }

    public Course getCourse(String code){
        Optional<Course> course = courseRepository.findByCode(Code);
        if(course.isPresent()){ 
            return course.get();
        }
        return null;
    }

    public boolean addUserToCourse(Long userId, String courseCode){
        Optional<User> user=userRepository.findById(id);
        Optional<Course> course = courseRepository.findByCode(Code);
        if(user.isPresent() && course.isPresent()){
            course.get().addUser(user.get());
            return true;
        }
        return false;
    }

    public boolean removeUserFromCourse(Long userId, String courseCode){
        Optional<User> user=userRepository.findById(id);
        Optional<Course> course = courseRepository.findByCode(Code);
        if(user.isPresent() && course.isPresent()){
            return course.get().removeUser(user);)
        }
        return false;
    }

    public boolean deleteUser(long id){
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){ 
            userRepository.delete(user.get());
            return true;
        }
        return false;

    }

    public boolean deleteCourse(){
        Optional<Course> course = courseRepository.findByCode(Code);
        if(course.isPresent()){ 
            courseRepository.delete(course.get());
            return true;
        }
        return false;
    }





}
