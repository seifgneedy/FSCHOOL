package com.fschool.fschool.Model.Entities;

import java.time.*;
import java.util.Objects;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;
@Entity(name = "Comment")
@Table(name = "comment")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
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
    @JoinColumn(name = "user_id")
    private User publisher;

    public Long getId(){
        return id;
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
