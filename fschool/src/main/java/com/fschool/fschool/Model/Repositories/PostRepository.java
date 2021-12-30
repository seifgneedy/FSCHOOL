package com.fschool.fschool.Model.Repositories;

import com.fschool.fschool.Model.Entities.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    
}
