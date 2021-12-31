package com.fschool.fschool.Model.Entities;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Course")
@Table(name = "courses")
public class Course {
    @Id
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "member_of",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id"))
    @JsonBackReference
    private Set<User> members = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "course_id")
    @JsonBackReference    
    private Set<Post> posts;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Course [code=" + code + ", members=" + members + ", name=" + name + "]";
    }


    public boolean addUser(User user) {
        members.add(user);
        return user.getCourses().add(this);
    }

    public boolean removeUser(User user) {
        user.getCourses().remove(this);
        return members.remove(user);
    }
    public boolean addPost(Post post){
        return posts.add(post);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
    
    
}
