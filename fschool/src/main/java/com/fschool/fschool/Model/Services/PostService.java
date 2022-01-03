package com.fschool.fschool.Model.Services;

import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.CourseRepository;
import com.fschool.fschool.Model.Repositories.PostRepository;
import com.fschool.fschool.Model.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    PostRepository postRepository;
    public List<Post> getPostsByType(String courseCode, String type, String userId) {
        Optional<Course> course = courseRepository.findByCode(courseCode);
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (!course.isPresent() || !user.isPresent())
            return null;
        if (course.get().getMembers().contains(user.get())) {
            List<Post> posts=course.get().getPosts()
                            .stream().filter(post->post.getType().equals(type)).toList();
                            
            posts.sort((first,second)->first.getDate().compareTo(second.getDate()));
            return posts;
        }
        return null;
    }
    public Long addPost(String courseCode,Post post,Long userId){
        Optional<User> publisher=userRepository.findById(userId);
        Optional<Course> course=courseRepository.findByCode(courseCode);
        if(!publisher.isPresent()||!course.isPresent())
            return 0L;
        if(course.get().getMembers().contains(publisher.get())){
            post.setPublisher(publisher.get());
            course.get().addPost(post);
            post=postRepository.save(post);
            return post.getId();
        }
        return 0L;
    }
}
