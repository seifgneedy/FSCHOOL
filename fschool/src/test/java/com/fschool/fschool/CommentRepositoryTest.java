package com.fschool.fschool;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CourseRepository courseRepository;
    @BeforeAll
    public static void addUsers(@Autowired UserRepository userRepository){
        User user = new User();
        user.setEmail("a@gmail.com");
        user.setRole("student");
        user.setFirstName("ahmed");
        user.setLastName("nagy");
        user.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user.setSex("m");
        user.setPassword(DigestUtils.sha256Hex("password"));

        userRepository.save(user);

        user = new User();
        user.setEmail("b@gmail.com");
        user.setRole("student");
        user.setFirstName("chloe");
        user.setLastName("todd");
        user.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("pass"));

        userRepository.save(user);

        user = new User();
        user.setEmail("c@gmail.com");
        user.setRole("teacher");
        user.setFirstName("kate");
        user.setLastName("smith");
        user.setBirthDate(LocalDate.of(1987, Month.SEPTEMBER, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("NOW_pass"));
        userRepository.save(user);
    }
    @BeforeAll
    public static void addCourses(@Autowired CourseRepository courseRepository){
        Course course = new Course();
        course.setCode("CSE 111");
        course.setName("Algorithms");
        course.setPosts(new HashSet<>());

        courseRepository.save(course);

        course=new Course();
        course.setCode("EED 22");
        course.setName("Electronics");
        course.setPosts(new HashSet<>());
        courseRepository.save(course);
    }
    @BeforeAll
    public static void addPosts(@Autowired PostRepository postRepository,
                                @Autowired CourseRepository courseRepository){
                                    
                                }
}
