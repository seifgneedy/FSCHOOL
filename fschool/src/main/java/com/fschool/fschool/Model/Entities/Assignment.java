package com.fschool.fschool.Model.Entities;

import java.time.*;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

@Entity(name = "Assignment")
@Table(name = "assignment")
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @SequenceGenerator(name = "AssignmentIDSequence", sequenceName = "AssignmentIDSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AssignmentIDSequence")
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime assigned_On;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_code", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "deliverables")
    private Set<Deliverable> deliverables = new HashSet<>();

    public Set<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(Set<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }

    public void addDeliverable(User user, String body) {
        Deliverable deliverable = new Deliverable(this, user);
        deliverable.setBody(body);
        deliverables.add(deliverable);
        user.getDeliverables().add(deliverable);
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAssigned_On() {
        return assigned_On;
    }

    public void setAssigned_On(LocalDateTime assigned_On) {
        this.assigned_On = assigned_On;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Assignment assignment = (Assignment) o;
        return id.equals(assignment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
