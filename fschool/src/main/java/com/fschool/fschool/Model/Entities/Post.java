package com.fschool.fschool.Model.Entities;

import java.time.*;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

@Entity(name="Post")
@Table(name="post")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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



    public Long getId(){
        return id;
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
