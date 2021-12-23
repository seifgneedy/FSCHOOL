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

    public Long addUser(User user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if (u.isPresent()){
            return 0L;
        }
        else {

            user.setPassword(hashPassword(user.getPassword()));
            userRepository.save(user);
            return user.getId();
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
            if(user.get().getRole().equals(role) && course.get().addUser(user.get())){
                    courseRepository.saveAndFlush(course.get());
                    userRepository.saveAndFlush(user.get());
                    return user.get().getFirstName() + " " + user.get().getLastName();
                }
            }
        return null;
        
    }

    public boolean removeUserFromCourse(Long userId, String courseCode) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Course> course = courseRepository.findByCode(courseCode);
        if (user.isPresent() && course.isPresent()) {
            Boolean res = course.get().removeUser(user.get());
            courseRepository.saveAndFlush(course.get());
            userRepository.saveAndFlush(user.get());
            return res;
        }
        return false;
    }

    public boolean deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            for(Course course: user.get().getCourses()){
                course.removeUser(user.get());
            }
            userRepository.delete(user.get());
            userRepository.flush();
            courseRepository.flush();
            return true;
        }
        return false;
    }

    public boolean deleteCourse(String code) {
        Optional<Course> course = courseRepository.findByCode(code);
        if (course.isPresent()) {
            courseRepository.delete(course.get());
            courseRepository.flush();
            userRepository.flush();
            return true;
        }
        return false;
    }


    //TODO test this method
    public List<User> getCourseMembers(String courseCode, String role){
        Optional<Course> course = courseRepository.findByCode(courseCode);
        List<User> users = new ArrayList<>();
        if(!course.isPresent())
            return null;

        for(User user : course.get().getMembers()){
            if(user.getRole().equals(role))
                users.add(user);
        }
        return users;
    }

    
    //TODO test this method
    public boolean updateCourseName(String courseCode, String courseName){
        Optional<Course> course = courseRepository.findByCode(courseCode);
        if (course.isPresent()) {
            course.get().setName(courseName);
            courseRepository.save(course.get());
            return true;
        }
        return false;
    }

    public User getUser(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        return null;
    }

    private String hashPassword(String password){
        return DigestUtils.sha256Hex(password);
    }






}
