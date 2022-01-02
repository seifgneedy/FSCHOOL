package com.fschool.fschool.Model.Services;

import java.util.*;

import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public List<Course> getCourses(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return new ArrayList<Course>(user.get().getCourses());
        }
        // very bad practice
        return null;
    }

    public Course getCourse(String code) {
        Optional<Course> course = courseRepository.findByCode(code);
        if (course.isPresent()) {
            return course.get();
        }
        return null;
    }

    // do we need this?
    /*public List<Post> getPosts(String courseCode) {
        Optional<Course> course = courseRepository.findByCode(courseCode);
        if (course.isPresent()) {
            return List.copyOf(course.get().getPosts());
        }
        return null;
    }*/

    // types should be --> post, announcement, question
    public List<Post> getPostsByType(String courseCode, String type, String userId) {
        Optional<Course> course = courseRepository.findByCode(courseCode);
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        List<Post> posts = new ArrayList<>();
        if (!course.isPresent() || !user.isPresent())
            return null;
        if (course.get().getMembers().contains(user.get())) {
            for (Post post : course.get().getPosts()) {
                if (post.getType().equals(type))
                    posts.add(post);
            }
            return posts;
        }
        return null;

    }

    public List<Comment> getComments(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return List.copyOf(post.get().getComments());
        }
        return null;
    }

}
