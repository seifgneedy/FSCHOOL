package com.fschool.fschool.Model.Services;
import java.time.LocalDateTime;
import java.util.*;

import com.fschool.fschool.Model.Entities.Post;
public class tst {
    public static void main(String[] args) {
        List<Post> posts = new ArrayList<>();
        Post first = new Post();
        first.setDate(LocalDateTime.now());
        Post second = new Post();
        second.setDate(LocalDateTime.of(2010,2,2,0,0));
        posts.add(first); posts.add(second);
        Post th=new Post(); th.setDate(LocalDateTime.of(2002, 2, 2, 1, 2, 4));
        posts.add(th);
        posts.sort((f,s)->f.getDate().compareTo(s.getDate()));
        for(Post p : posts)
            System.out.println(p.getDate());
    }
}
