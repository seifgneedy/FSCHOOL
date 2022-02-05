package com.fschool.fschool.Model.Entities;

import java.time.*;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;


@Embeddable
@Entity(name = "Deliverable")
@Table(name = "deliverable")
public class Deliverable {
    public Deliverable(){}
    public Deliverable(Assignment assignment, User user) {
        this.assignment = assignment;
        this.user = user;
        this.id = new DeliverableID(assignment.getId(), user.getId());
    }

    @EmbeddedId
    private DeliverableID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assignemntId")
    @JsonBackReference(value = "assignment")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime SubmissionDate;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String grade = "-";


    public DeliverableID getId() {
        return id;
    }

    public void setId(DeliverableID id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getSubmissionDate() {
        return SubmissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        SubmissionDate = submissionDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Deliverable that = (Deliverable) o;
        return Objects.equals(assignment, that.assignment) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignment, user);
    }

}