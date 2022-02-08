package com.fschool.fschool.Controllers;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import com.fschool.fschool.Model.Entities.Assignment;
import com.fschool.fschool.Model.Entities.Deliverable;
import com.fschool.fschool.Model.Services.AssignmentService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = { "http://localhost:8080" },
            allowCredentials = "true")
@RestController
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;


    @GetMapping(path="assignments")
    public List<Assignment> getAssignments(@CookieValue("id") String id){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        List<Assignment> list = assignmentService.getAssignments(Long.valueOf(id));
        return list;
    }

    @GetMapping(path="deliverables")
    public List<Deliverable> getDeliverables(@CookieValue("id") String id){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        List<Deliverable> list = assignmentService.getDeliverables(Long.valueOf(id));
        return list;
    }

    @GetMapping(path="courseAssignments")
    public List<Assignment> getCourseAssignments(@CookieValue("id") String id,@RequestParam String courseCode){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return null;
        }
        List<Assignment> list = assignmentService.getCourseAssignments(Long.valueOf(id),courseCode);
        return list;
    }

    @GetMapping(path="assignmentDeliverables")
    public List<Deliverable> getAssignmentDeliverables(@CookieValue("id") String id,@RequestParam Long assignmentId) {
        return assignmentService.getAssignmentDeliverables(Long.valueOf(id), assignmentId);
    }

    @PostMapping(path="assignment")
    public boolean addAssignment(@CookieValue("id") String id, @RequestParam String courseCode,@RequestBody String assignmentObject){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JSONObject assignmentJson = new JSONObject(assignmentObject);
        Assignment assignment = new Assignment();

        assignment.setBody(assignmentJson.getString("body"));
        assignment.setTitle(assignmentJson.getString("title"));
        assignment.setDueDate(LocalDateTime.parse(assignmentJson.getString("dueDate"), formatter));
        return assignmentService.addAssignment(Long.valueOf(id), assignment, courseCode);
    }

    @PostMapping(path="deliverable")
    public boolean addDeliverable(@CookieValue("id") String id, @RequestParam Long assignmentId,@RequestBody String body){
        if(id.isEmpty()){
            //Session expired or not logged in.
            return false;
        }
        System.out.println(id);
        System.out.println(assignmentId);
        System.out.println(body);
        return assignmentService.addDeliverable(Long.valueOf(id), assignmentId, body);
    }

    @PostMapping(path="grade")
    public boolean grade(@RequestParam Long assignmentId, @RequestParam Long studentId,@RequestBody String grade){
        return assignmentService.gradeDeliverable(assignmentId, studentId, grade);
    }
    
}
