package com.fschool.fschool.Model.Services;

import java.util.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class AssignmentService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    DeliverableRepository deliverableRepository;

    //get a student's assignments
    public List<Assignment> getAssignments(Long id){
        Optional <User> user = userRepository.findById(id);
        if(!user.isPresent())
            return null;
        Set<Course> courses = user.get().getCourses();
        List<Assignment> assignments = new ArrayList<>();
        for(Course c : courses){
            assignments.addAll(c.getAssignments());
        }
        return assignments;
    }


    public List<Deliverable> getDeliverables(Long id){
        Optional <User> user = userRepository.findById(id);
        if(!user.isPresent())
            return null;
        List<Deliverable> deliverables =  new ArrayList<Deliverable>(user.get().getDeliverables());
        if(deliverables.size()>1)
            deliverables.sort((first,second)->first.getSubmissionDate().compareTo(second.getSubmissionDate()));
        return deliverables;
    }

    public List<Assignment> getCourseAssignments(Long id, String courseCode){
        Optional<Course> course = courseRepository.findByCode(courseCode);
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent() || !course.isPresent() ||!course.get().getMembers().contains(user.get())){
            return null;
        }
        List<Assignment> assignments = new ArrayList<Assignment>(course.get().getAssignments());
        if(assignments.size()>1)
            assignments.sort((first,second)->first.getDueDate().compareTo(second.getDueDate()));
        return assignments;
    }

    public boolean addAssignment(Long id, Assignment assignment, String courseCode){
        Optional<Course> course = courseRepository.findByCode(courseCode);
        Optional<User> user = userRepository.findById(id);
        System.out.println(user.get());
        System.out.println(course.get());
        if(!user.isPresent() || !course.isPresent() || !user.get().getRole().equals("teacher"))
            return false;
        assignment.setCourse(course.get());
        assignmentRepository.save(assignment);
        return true;
    }

    public boolean addDeliverable(Long id, Long assignmentId, String body){
        Optional<User> user = userRepository.findById(id);
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if(!user.isPresent() || !assignment.isPresent())
            return false;
        Deliverable deliverable = new Deliverable(assignment.get(), user.get());
        deliverable.setBody(body);
        deliverable.setGrade("-");  
        deliverableRepository.save(deliverable);
        return true;
    }

    public List<Deliverable> getAssignmentDeliverables(Long userId, Long assignmentId){
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent() || !assignment.isPresent() || !user.get().getRole().equals("teacher"))
            return null;
        List<Deliverable> deliverables = new ArrayList<Deliverable>(assignment.get().getDeliverables());
        if(deliverables.size()>1)
            deliverables.sort((first,second)->first.getSubmissionDate().compareTo(second.getSubmissionDate()));
        return deliverables;
    }

    public boolean gradeDeliverable(Long assignmentId, Long studentId ,String grade){
        DeliverableID deliverableID = new DeliverableID(assignmentId, studentId);
        Optional<Deliverable> deliverable = deliverableRepository.findById(deliverableID);
        if(!deliverable.isPresent())
            return false;
        deliverable.get().setGrade(grade);
        deliverableRepository.save(deliverable.get());
        return true;
    }
    
}