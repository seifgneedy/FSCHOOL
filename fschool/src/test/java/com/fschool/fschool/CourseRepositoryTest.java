package com.fschool.fschool;

import com.fschool.fschool.Model.Repositories.CourseRepository;
import com.fschool.fschool.Model.Repositories.UserRepository;
import com.fschool.fschool.Model.Entities.*;

import java.time.*;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;


    private User user1;
    private User user2;
    private User user3;
    private List<User> userList = new ArrayList<>();

    
    private void addUsers(){
        user1 = new User();
        user1.setEmail("a@gmail.com");
        user1.setRole("student");
        user1.setFirstName("ahmed");
        user1.setLastName("nagy");
        user1.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user1.setSex('m');
        user1.setPassword(DigestUtils.sha256Hex("password"));

        user2 = new User();
        user2.setEmail("b@gmail.com");
        user2.setRole("student");
        user2.setFirstName("chloe");
        user2.setLastName("todd");
        user2.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user2.setSex('f');
        user2.setPassword(DigestUtils.sha256Hex("pass"));

        
        user3 = new User();
        user3.setEmail("c@gmail.com");
        user3.setRole("teacher");
        user3.setFirstName("kate");
        user3.setLastName("smith");
        user3.setBirthDate(LocalDate.of(1987, Month.SEPTEMBER, 11));
        user3.setSex('f');
        user3.setPassword(DigestUtils.sha256Hex("NOW_pass"));

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

    }

    @Test
    @Order(1)
    public void sizeTest() {
        List<Course> list = courseRepository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(courseRepository.count());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void addOneCourseTest() {
        Course course = new Course();
        course.setCode("CSE 123");
        course.setName("Computer Vision");
        courseRepository.save(course);

        Assertions.assertThat(courseRepository.findByCode("CSE 123").get().getName()).isEqualTo("Computer Vision");
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void addAnotherCourseTese() {
        Course course = new Course();
        course.setCode("CSE 321");
        course.setName("Data Structure");
        courseRepository.save(course);

        Assertions.assertThat(courseRepository.findByCode("CSE 123").get().getName().equals("Computer Vision"));
        Assertions.assertThat(courseRepository.count()).isEqualTo(2L);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateCourse() {
        Course course = courseRepository.findByCode("CSE 321").get();
        course.setName("Algorithms");
        courseRepository.save(course);

        Assertions.assertThat(courseRepository.findByCode("CSE 321").get().getName()).isEqualTo("Algorithms");
        Assertions.assertThat(courseRepository.findByName("Algorithms").get().getCode()).isEqualTo("CSE 321");

    }

    
    @Test
    @Order(5)
    public void addUserToCourseTest(){
        addUsers();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        Course course = courseRepository.findByCode("CSE 123").get();
        course.addUser(user1);
        course.addUser(user2);
        course.addUser(user3);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(3);
        Assertions.assertThat(user1.getCourses().size()).isEqualTo(1);
        Assertions.assertThat(user2.getCourses().size()).isEqualTo(1);
        Assertions.assertThat(user3.getCourses().size()).isEqualTo(1);



        Course courseAfter = courseRepository.findByCode("CSE 123").get();
        Assertions.assertThat(courseAfter.getMembers().size()).isEqualTo(3);
        courseAfter.removeUser(user1);
        courseAfter.removeUser(user2);
        courseRepository.flush();
        Assertions.assertThat(courseAfter.getMembers().size()).isEqualTo(1);
        Assertions.assertThat(user1.getCourses().size()).isEqualTo(0);
        Assertions.assertThat(user2.getCourses().size()).isEqualTo(0);
        Assertions.assertThat(user3.getCourses().size()).isEqualTo(1);


    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void deleteCourseTest() {
        Course course = courseRepository.findByCode("CSE 123").get();
        courseRepository.delete(course);
        Optional<Course> isFound = courseRepository.findByCode("CSE 123");
        Assertions.assertThat(isFound.isPresent()).isFalse();
    }

    @Test
    @Order(8)
    public void newSizeTest() {
        Assertions.assertThat(courseRepository.count()).isEqualTo(1L);
    }

    @Test
    @Order(9)
    @Rollback(value = false)
    public void addAfterDeletionTest() {
        Optional<Course> deletedCourse = courseRepository.findByCode("CSE 123");
        Assertions.assertThat(deletedCourse.isPresent()).isFalse();
        Course course = new Course();
        course.setCode("CSE 258");
        course.setName("Networks");
        courseRepository.save(course);
        Assertions.assertThat(courseRepository.findByCode("CSE 258").isPresent()).isTrue();
        Assertions.assertThat(courseRepository.findByName("Networks").isPresent()).isTrue();

    }

    @Test
    @Order(10)
    public void nonExistingIdTest() {
        Optional<Course> course = courseRepository.findByCode("totaly wrong code");
        Assertions.assertThat(course.isPresent()).isFalse();
    }


}
