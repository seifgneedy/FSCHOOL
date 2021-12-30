package com.fschool.fschool;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Services.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TeacherServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CourseRepository courseRepository;

    @Autowired
    @InjectMocks
    private TeacherService teacherService;

    private List<User> teacherList = new ArrayList<>();
    private List<Course> courseList =  new ArrayList<>();


    
    private void addUsers(){
        teacherList = new ArrayList<>();
        User user;
        user = new User();
        user.setEmail("a@gmail.com");
        user.setRole("teacher");
        user.setFirstName("ahmed");
        user.setLastName("nagy");
        user.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user.setSex("m");
        user.setPassword(DigestUtils.sha256Hex("password"));
        teacherList.add(user);

        user = new User();
        user.setEmail("b@gmail.com");
        user.setRole("teacher");
        user.setFirstName("chloe");
        user.setLastName("todd");
        user.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("pass"));
        teacherList.add(user);
        
        user = new User();
        user.setEmail("c@gmail.com");
        user.setRole("teacher");
        user.setFirstName("kate");
        user.setLastName("smith");
        user.setBirthDate(LocalDate.of(1987, Month.SEPTEMBER, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("NOW_pass"));
        teacherList.add(user);
    }

    private void addCourses(){
        courseList = new ArrayList<>();
        Course course;
        course = new Course();
        course.setCode("CSE 123");
        course.setName("Computer Vision");
        courseList.add(course);

        course = new Course();
        course.setCode("CSE 321");
        course.setName("Data Structure");
        courseList.add(course);
    }
    private void addUsersToCourses(){
        // user 0 is in both courses
        courseList.get(0).addUser(teacherList.get(0));
        courseList.get(1).addUser(teacherList.get(0));
        // user 1 is only in course CSE 123
        courseList.get(0).addUser(teacherList.get(1));
        // user 2 is not in any course
    }
    private void prepareDB(){
        addUsers();
        addCourses();
        addUsersToCourses();
    }
    @Test
    public void getCoursesOfUserInASingleCourse(){
        prepareDB();
        User user = teacherList.get(1);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        List<Course> courses = teacherService.getCourses(user.getId());
        assertNotNull(courses);
        assertEquals(1, courses.size());
        assertEquals(courseList.get(0).getCode(), courses.get(0).getCode());
    }

    @Test
    public void getCoursesOfUserInMultipleCourses(){
        prepareDB();
        User user = teacherList.get(0);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        List<Course> courses = teacherService.getCourses(teacherList.get(0).getId());
        assertNotNull(courses);
        assertEquals(2, courses.size());
        assertEquals(courseList.get(0).getCode(), courses.get(0).getCode());
        assertEquals(courseList.get(1).getCode(), courses.get(1).getCode());
    }

    @Test
    public void getCoursesOfUserNotInAnyCourse(){
        prepareDB();
        User user = teacherList.get(2);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        List<Course> courses = teacherService.getCourses(user.getId());
        assertEquals(0, courses.size());
    }
    @Test
    public void getCoursesOfUserNotInDB(){
        prepareDB();
        when(userRepository.findById(9999L)).thenReturn(Optional.ofNullable(null));
        List<Course> courses = teacherService.getCourses(9999L);
        assertNull(courses);
    }
    @Test
    public void getCourseWithCodeInDB(){
        addCourses();
        when(courseRepository.findByCode("CSE 123")).thenReturn(Optional.of(courseList.get(0)));
        Course course = teacherService.getCourse("CSE 123");
        assertEquals("Computer Vision", course.getName());
    }
    @Test
    public void getCourseWithCodeNotInDB(){
        addCourses();
        when(courseRepository.findByCode("CSE 999")).thenReturn(Optional.ofNullable(null));
        Course course = teacherService.getCourse("CSE 999");
        assertNull(course);
    }
}

