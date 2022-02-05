package com.fschool.fschool.Model.Entities;



import java.io.Serializable;
import java.util.*;

import javax.persistence.*;



@Embeddable
public class DeliverableID implements Serializable{

    public DeliverableID(){}
    public DeliverableID(Long assignmentId, Long userId) {
        this.assignmentId = assignmentId;
        this.userId = userId;
    }

    @Column(name = "assignment_id",nullable = false)
    private Long assignmentId;
 
    @Column(name = "user_id",nullable = false)
    private Long userId;

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        DeliverableID that = (DeliverableID) o;
        return Objects.equals(assignmentId, that.assignmentId) &&
               Objects.equals(userId, that.userId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, userId);
    }
    
}
