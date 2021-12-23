package com.fschool.fschool;

import com.fschool.fschool.Model.Repositories.CourseRepository;
import com.fschool.fschool.Model.Entities.*;

import java.util.*;

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
    @Order(6)
    @Rollback(value = false)
    public void deleteCourseTest() {
        Course course = courseRepository.findByCode("CSE 321").get();
        courseRepository.delete(course);
        Optional<Course> isFound = courseRepository.findByCode("CSE 321");
        Assertions.assertThat(isFound.isPresent()).isFalse();
    }

    @Test
    @Order(7)
    public void newSizeTest() {
        Assertions.assertThat(courseRepository.count()).isEqualTo(1L);
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    public void addAfterDeletionTest() {
        Optional<Course> deletedCourse = courseRepository.findByCode("CSE 321");
        Assertions.assertThat(deletedCourse.isPresent()).isFalse();
        Course course = new Course();
        course.setCode("CSE 258");
        course.setName("Networks");
        courseRepository.save(course);
        Assertions.assertThat(courseRepository.findByCode("CSE 258").isPresent()).isTrue();
        Assertions.assertThat(courseRepository.findByName("Networks").isPresent()).isTrue();

    }

    @Test
    @Order(9)
    public void nonExistingIdTest() {
        Optional<Course> course = courseRepository.findByCode("totaly wrong code");
        Assertions.assertThat(course.isPresent()).isFalse();
    }


}
