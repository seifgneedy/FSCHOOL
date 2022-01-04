package com.fschool.fschool;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired 
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @BeforeAll
    public static void addUsers(@Autowired PostRepository postRepository,
                                @Autowired CourseRepository courseRepository,
                                @Autowired UserRepository userRepository){
        User user = new User();
        user.setEmail("a@gmail.com");
        user.setRole("student");
        user.setFirstName("ahmed");
        user.setLastName("nagy");
        user.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user.setSex("m");
        user.setPassword(DigestUtils.sha256Hex("password"));

        user = userRepository.save(user);

        User user2 = new User();
        user2.setEmail("b@gmail.com");
        user2.setRole("student");
        user2.setFirstName("chloe");
        user2.setLastName("todd");
        user2.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user2.setSex("f");
        user2.setPassword(DigestUtils.sha256Hex("pass"));

        user2 =userRepository.save(user);

        Course course = new Course();
        course.setCode("CSE 111");
        course.setName("Algorithms");
        course.setPosts(new HashSet<>());
        course.addUser(user);
        course.addUser(user2);
        course=courseRepository.save(course);
        
        Post post = new Post();
        post.setType("announcement");
        post.setTitle("title");
        post.setBody("Body1");
        post.setPublisher(user);
        course.addPost(post);
        postRepository.save(post);

        post = new Post();
        post.setType("announcement");
        post.setTitle("Title2");
        post.setBody("Body2");
        post.setPublisher(user2);
        course.addPost(post);
        postRepository.save(post);
    }
    @Test 
    @Order(1)
    public void injectedComponentsIsNotNull() {
        Assertions.assertThat(postRepository).isNotNull();
        Assertions.assertThat(courseRepository).isNotNull();
        Assertions.assertThat(commentRepository).isNotNull();
    }
    @Test
    @Order(2)
    public void noCommentsInPost(){
        Optional<Post> post=postRepository.findById(1L);
        Assertions.assertThat(post.get().getComments().size()).isEqualTo(0);
    }
}
