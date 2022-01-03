package com.fschool.fschool.Model.Services;

import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.CommentRepository;
import com.fschool.fschool.Model.Repositories.PostRepository;
import com.fschool.fschool.Model.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    public List<Comment> getComments(Long postId){
        Optional<Post> post=postRepository.findById(postId);
        if(post.isPresent()){
            List<Comment> comments=new ArrayList<>(post.get().getComments());
            comments.sort((first,second)->first.getDate().compareTo(second.getDate()));
            return comments;
        }
        return null;
    }

    public Long addComment(Long postId,Comment comment,Long userId){
        Optional<User> publisher=userRepository.findById(userId);
        Optional<Post> post=postRepository.findById(postId);
        if(!publisher.isPresent()||!post.isPresent())
            return 0L;
        comment.setPublisher(publisher.get());
        post.get().addComment(comment);
        comment=commentRepository.save(comment);
        return comment.getId();
    }
}
