package com.fschool.fschool.Model.Entities;

import java.util.*;

import javax.persistence.*;

@Entity(name = "Course")
@Table(name = "courses")
public class Course {
    @Id
    @Column(nullable = false)
    private String code;
    @Column(nullable = false, unique = true)
    private String name;
    //TODO:
    @ManyToMany(cascade = {})       
    @JoinTable(name = "member_of",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<User> members = new HashSet<>();



    public Course(String code, String name, Set<User> members) {
        this.code = code;
        this.name = name;
        this.members = members;
    }

    public Course() {
    }

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


    public void addUser(User user) {
        members.add(user);
        user.getCourses().add(this);
    }

    public void removeUser(User user) {
        members.remove(user);
        user.getCourses().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return code != null && code.equals(((Course) o).getCode());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    
}
