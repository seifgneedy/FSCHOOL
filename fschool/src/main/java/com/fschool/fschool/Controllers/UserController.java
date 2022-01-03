package com.fschool.fschool.Controllers;

import java.util.*;


import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Services.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = { "http://localhost:8080" },
            allowCredentials = "true")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @GetMapping(path="courses")
    public List<Course> getCourses(@CookieValue("id") String id){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        return userService.getCourses(Long.valueOf(id));
    }

    @GetMapping(path="courses/{courseCode}")
    public List<Post> getPosts(@CookieValue("id") String id,
                                @PathVariable("courseCode") String courseCode, 
                                @RequestParam String PostType){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        // Check if user in course --> done in service class
        return postService.getPostsByType(courseCode, PostType, id);
    }
    @GetMapping(path="comments/{postId}")
    public List<Comment> getComments(@CookieValue("id") String id,
                                @PathVariable String postId){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }

        // Check if user in course --> done in service class
        //TODO  
        return userService.getComments(Long.valueOf(postId));
    }
    @PostMapping(path="post")
    public boolean addPost(@CookieValue("id") String id,
                            @RequestParam String courseCode,
                            @RequestBody String postString){
                                //TODO: try to parse the string as Post object
        if(id.isEmpty()){
            return false;
        }
        JSONObject postJson = new JSONObject(postString);
        Post post = new Post();
        post.setTitle(postJson.getString("title"));
        post.setType(postJson.getString("type"));
        post.setBody(postJson.getString("body"));
        return postService.addPost(courseCode, post,Long.valueOf(id));
    }
}
