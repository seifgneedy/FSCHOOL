package com.fschool.fschool.Model.Entities;

import java.time.*;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

@Entity(name="Post")
@Table(name="post")
@NoArgsConstructor @AllArgsConstructor
public class Post {
    @Id
    @SequenceGenerator(name = "PostIDSequence",
                        sequenceName = "PostIDSequence", 
                        allocationSize = 1
                        )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "PostIDSequence")
    @Column(nullable = false,updatable = false)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime date;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User publisher;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Set<Comment> comments;


    public void setId(Long id){
        this.id=id;
    }

    public Long getId(){
        return id;
    }

    public void setDate(LocalDateTime date){
        this.date=date;
    }
    public LocalDateTime getDate(){
        return date;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }
    public void setBody(String body){
        this.body=body;
    }
    public String getBody(){
        return body;
    }
    public void setPublisher(User publisher){
        this.publisher=publisher;
    }
    public User getPublisher(){
        return publisher;
    }
    public void setComments(Set<Comment> comments){
        this.comments=comments;
    }
    public Set<Comment> getComments(){
        return comments;
    }

    public boolean addComment(Comment comment){
        return comments.add(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
