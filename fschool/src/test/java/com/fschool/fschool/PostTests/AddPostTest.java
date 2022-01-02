package com.fschool.fschool.PostTests;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddPostTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired 
    private CourseRepository courseRepository;
    @Autowired 
    private UserRepository userRepository;

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
    @Test 
    @Order(1)
    public void injectedCompIsNotNull() {
        Assertions.assertThat(postRepository).isNotNull();
        Assertions.assertThat(courseRepository).isNotNull();
    }
    @Test
    @Order(2)
    public void noPostsInCourseTest(){
        Set<Post> posts=courseRepository.findByCode("CSE 111").get().getPosts();
        Assertions.assertThat(posts.size()).isEqualTo(0);
        
    }
    @Test
    @Order(3)
    @Rollback(value = false)
    public void addPostToCourseTest(){
        Optional<Course> course = courseRepository.findByCode("CSE 111");
        Post post =new Post();
        post.setTitle("Title");
        post.setBody("Hi I'm testing here");
        Optional<User> publisher= userRepository.findByEmail("a@gmail.com");
        post.setPublisher(publisher.get());
        post.setType("post");
        course.get().addPost(post);
        courseRepository.save(course.get());
        
        Optional<Course> courseWithPost = courseRepository.findByCode("CSE 111");
        // check post added correctly
        Assertions.assertThat(courseWithPost.get().getPosts().size()).isEqualTo(1);

        Iterator<Post> postIter=courseWithPost.get().getPosts().iterator();
        Post upPost=postIter.next();

        Assertions.assertThat(upPost.getId()).isEqualTo(1L);
        Assertions.assertThat(upPost.getTitle()).isEqualTo("Title");
        // check the publisher
        User postPublisher = upPost.getPublisher();
        Assertions.assertThat(postPublisher.getEmail()).isEqualTo("a@gmail.com");
    }
    @Test
    @Order(4)
    public void addPostToNotExistingCourse(){
        
    }
}
