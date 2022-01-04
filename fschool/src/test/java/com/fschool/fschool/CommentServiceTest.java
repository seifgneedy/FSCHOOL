package com.fschool.fschool;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Services.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;

    @Autowired
    @InjectMocks
    private CommentService commentService;

    private List<User> userList = new ArrayList<>();
    private List<Course> courseList =  new ArrayList<>();
    private List<Post> postList =  new ArrayList<>();
    private List<Comment> commentList =  new ArrayList<>();


    public void addUsers(){
        User user = new User();
        user.setEmail("a@gmail.com");
        user.setRole("student");
        user.setFirstName("ahmed");
        user.setLastName("nagy");
        user.setId(0L);
        user.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user.setSex("m");
        user.setPassword(DigestUtils.sha256Hex("password"));

        userList.add(user);

        user = new User();
        user.setEmail("b@gmail.com");
        user.setRole("student");
        user.setFirstName("chloe");
        user.setLastName("todd");
        user.setId(1L);
        user.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("pass"));

        userList.add(user);

        user = new User();
        user.setEmail("c@gmail.com");
        user.setRole("teacher");
        user.setFirstName("kate");
        user.setLastName("smith");
        user.setId(2L);
        user.setBirthDate(LocalDate.of(1987, Month.SEPTEMBER, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("NOW_pass"));
        userList.add(user);
    }

    public void addCourses(){
        Course course = new Course();
        course.setCode("CSE 111");
        course.setName("Algorithms");
        course.setPosts(new HashSet<>());

        courseList.add(course);

        course=new Course();
        course.setCode("EED 22");
        course.setName("Electronics");
        course.setPosts(new HashSet<>());
        courseList.add(course);
    }

    public void addPosts(){
        Post post = new Post();
        post.setTitle("Title1");
        post.setBody("Body1");
        post.setId(0L);
        post.setComments(new HashSet<>());
        post.setPublisher(userList.get(0));
        courseList.get(0).addPost(post);
    
        postList.add(post);

        post = new Post();
        post.setTitle("Title2");
        post.setBody("Body2");
        post.setId(1L);
        post.setComments(new HashSet<>());
        post.setPublisher(userList.get(1));
        courseList.get(0).addPost(post);
        postList.add(post);

        post = new Post();
        post.setTitle("Title3");
        post.setBody("Body3");
        post.setId(2L);
        post.setComments(new HashSet<>());
        post.setPublisher(userList.get(2));
        courseList.get(0).addPost(post);
        postList.add(post);
    }


    
    public void addComments(){
        Comment comment = new Comment();
        comment.setBody("Comment Body1");
        comment.setPublisher(userList.get(0));
        comment.setId(0L);
        comment.setDate(LocalDateTime.now());
        postList.get(0).addComment(comment);
        comment.setPost(postList.get(0));
        commentList.add(comment);

        comment = new Comment();
        comment.setBody("Comment Body2");
        comment.setPublisher(userList.get(2));
        comment.setId(1L);
        comment.setDate(LocalDateTime.now());
        postList.get(0).addComment(comment);
        comment.setPost(postList.get(0));
        commentList.add(comment);

        comment = new Comment();
        comment.setBody("Comment Body3");
        comment.setPublisher(userList.get(1));
        comment.setId(2L);
        comment.setDate(LocalDateTime.now());
        postList.get(1).addComment(comment);
        comment.setPost(postList.get(1));
        commentList.add(comment);
    }


    @Test
    @Order(1)
    public void getCommentsCorrectPostTest(){
        addUsers();
        addCourses();
        addPosts();
        addComments();
        Post post = postList.get(0);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        List<Comment> returnedComments = commentService.getComments(post.getId());
        Assertions.assertThat(returnedComments).isNotNull();
        Assertions.assertThat(returnedComments.size()).isEqualTo(2);

    }

    @Test
    @Order(2)
    public void getCommentWrongPostId(){
        when(postRepository.findById(5L)).thenReturn(Optional.ofNullable(null));
        List<Comment> returnedComments = commentService.getComments(5L);
        Assertions.assertThat(returnedComments).isNull();
    }

    @Test
    @Order(3)
    public void addCommentTest(){
        addUsers();
        addCourses();
        addPosts();
        addComments();
        Post post = postList.get(0);
        User publisher = userList.get(2);
        Comment comment = commentList.get(0);
        when(userRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(commentRepository.save(comment)).thenReturn(comment);
        Assertions.assertThat(commentService.addComment(post.getId(), comment, publisher.getId())).isEqualTo(comment);

    }

    
    @Test
    @Order(4)
    public void addCommentNoPostTest(){
        addUsers();
        addCourses();
        addPosts();
        addComments();
        User publisher = userList.get(2);
        Comment comment = commentList.get(0);
        when(userRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThat(commentService.addComment(0L, comment, publisher.getId())).isNull();

    }

    @Test
    @Order(5)
    public void addCommentNoPublisherTest(){
        addUsers();
        addCourses();
        addPosts();
        addComments();
        Post post = postList.get(0);
        Comment comment = commentList.get(0);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Assertions.assertThat(commentService.addComment(post.getId(), comment, 0L)).isNull();

    }



}