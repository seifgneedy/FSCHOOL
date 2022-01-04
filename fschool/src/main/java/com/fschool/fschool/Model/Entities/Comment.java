package com.fschool.fschool.Model.Entities;

import java.time.*;
import java.util.Objects;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;
@Entity(name = "Comment")
@Table(name = "comment")
@AllArgsConstructor @NoArgsConstructor
public class Comment {
    @Id
    @SequenceGenerator(name = "CommentIDSequence",
                        sequenceName = "CommentIDSequence", 
                        allocationSize = 1
                        )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "CommentIDSequence")
    @Column(nullable = false,updatable = false)
    private Long id;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "post")
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User publisher;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
