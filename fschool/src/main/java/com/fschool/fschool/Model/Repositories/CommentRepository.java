package com.fschool.fschool.Model.Repositories;

import com.fschool.fschool.Model.Entities.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    
}
