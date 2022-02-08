package com.fschool.fschool;


import static org.mockito.Mockito.when;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.*;
import com.fschool.fschool.Model.Repositories.*;
import com.fschool.fschool.Model.Services.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssignmentServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private DeliverableRepository deliverableRepository;

    @Autowired
    @InjectMocks
    private AssignmentService assignmentService;

    private List<User> userList = new ArrayList<>();
    private List<Course> courseList =  new ArrayList<>();
    private List<Assignment> assignments = new ArrayList<>();
    private List<Deliverable> deliverables = new ArrayList<>();


    public void addUsers(){
        User user = new User();
        user.setEmail("a@gmail.com");
        user.setRole("student");
        user.setFirstName("ahmed");
        user.setLastName("nagy");
        user.setId(0L);
        user.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user.setSex("m");
        user.setPassword(DigestUtils.sha256Hex("password"));

        userList.add(user);

        user = new User();
        user.setEmail("b@gmail.com");
        user.setRole("student");
        user.setFirstName("chloe");
        user.setLastName("todd");
        user.setId(1L);
        user.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("pass"));

        userList.add(user);

        user = new User();
        user.setEmail("c@gmail.com");
        user.setRole("teacher");
        user.setFirstName("kate");
        user.setLastName("smith");
        user.setId(2L);
        user.setBirthDate(LocalDate.of(1987, Month.SEPTEMBER, 11));
        user.setSex("f");
        user.setPassword(DigestUtils.sha256Hex("NOW_pass"));
        userList.add(user);
    }

    public void addCourses(){
        Course course = new Course();
        course.setCode("CSE111");
        course.setName("Algorithms");
        course.setPosts(new HashSet<>());
        course.addUser(userList.get(0));
        course.addUser(userList.get(2));

        courseList.add(course);

        course=new Course();
        course.setCode("EED22");
        course.setName("Electronics");
        course.setPosts(new HashSet<>());
        course.addUser(userList.get(0));
        course.addUser(userList.get(2));
        courseList.add(course);
    }

    public void addAssignments(){
        Assignment assignment = new Assignment();
        assignment.setTitle("Lab 1");
        assignment.setBody("Lab 1 Body");
        assignment.setAssigned_On(LocalDateTime.now());
        assignment.setDueDate(LocalDateTime.now().plusDays(5L));
        assignment.setCourse(courseList.get(0));
        assignment.setId(0L);
        assignments.add(assignment);

        Assignment assignment2 = new Assignment();
        assignment2.setTitle("Lab 2");
        assignment2.setBody("Lab 2 Body");
        assignment2.setAssigned_On(LocalDateTime.now());
        assignment2.setDueDate(LocalDateTime.now().plusDays(5L));
        assignment2.setCourse(courseList.get(0));
        assignment2.setId(1L);
        assignments.add(assignment2);

        courseList.get(0).addAssignment(assignment);
        courseList.get(0).addAssignment(assignment2);

    }


    
    public void addDeliverables(){
        Deliverable deliverable = new Deliverable();
        DeliverableID id = new DeliverableID(0L, 0L);
        deliverable.setAssignment(assignments.get(0));
        deliverable.setBody("Lab 1 Submission");
        deliverable.setSubmissionDate(LocalDateTime.now());
        deliverable.setUser(userList.get(0));
        deliverable.setId(id);
        deliverables.add(deliverable);

    }

    @Test
    @Order(1)
    public void getAssignmentsTest(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        when(userRepository.findById(0L)).thenReturn(Optional.of(userList.get(0)));
        List<Assignment> returned = assignmentService.getAssignments(0L);
        Assertions.assertThat(returned.size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    public void getAssignmentsTest2(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        when(userRepository.findById(2L)).thenReturn(Optional.of(userList.get(2)));
        List<Assignment> returned = assignmentService.getAssignments(2L);
        Assertions.assertThat(returned.size()).isEqualTo(2);
    }

    @Test
    @Order(3)
    public void getAssignmentsTest3(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userList.get(1)));
        List<Assignment> returned = assignmentService.getAssignments(1L);
        Assertions.assertThat(returned.size()).isEqualTo(0);
    }

    @Test
    @Order(4)
    public void getDelivrablesTest(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        Deliverable d = deliverables.get(0);
        d.getAssignment().addDeliverable(userList.get(0), d.getBody());
        when(userRepository.findById(0L)).thenReturn(Optional.of(userList.get(0)));
        List<Deliverable> returned = assignmentService.getDeliverables(0L);
        Assertions.assertThat(returned.size()).isEqualTo(1);
        Assertions.assertThat(returned.get(0).getBody()).isEqualTo(d.getBody());
    }

    @Test
    @Order(5)
    public void getDelivrablesTest2(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        Deliverable d = deliverables.get(0);
        d.getAssignment().addDeliverable(userList.get(0), d.getBody());
        when(userRepository.findById(1L)).thenReturn(Optional.of(userList.get(1)));
        List<Deliverable> returned = assignmentService.getDeliverables(1L);
        Assertions.assertThat(returned.size()).isEqualTo(0);
    }

    @Test
    @Order(6)
    public void getAssignmentDeliverablesTest(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        Deliverable d = deliverables.get(0);
        d.getAssignment().addDeliverable(userList.get(0), d.getBody());
        when(userRepository.findById(2L)).thenReturn(Optional.of(userList.get(2)));
        when(assignmentRepository.findById(0L)).thenReturn(Optional.of(assignments.get(0)));
        List<Deliverable> returned = assignmentService.getAssignmentDeliverables(2L,0L);
        Assertions.assertThat(returned.size()).isEqualTo(1);


    }
    @Test
    @Order(7)
    public void getAssignmentDeliverablesTest2(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        Deliverable d = deliverables.get(0);
        d.getAssignment().addDeliverable(userList.get(0), d.getBody());
        when(userRepository.findById(0L)).thenReturn(Optional.of(userList.get(0)));
        when(assignmentRepository.findById(0L)).thenReturn(Optional.of(assignments.get(0)));
        Assertions.assertThat(assignmentService.getAssignmentDeliverables(0L,0L)).isNull();
    }

    @Test
    @Order(8)
    public void gradeDeliverableTest(){
        addUsers();
        addCourses();
        addAssignments();
        addDeliverables();
        Deliverable d = deliverables.get(0);
        Assertions.assertThat(d.getGrade()).isEqualTo("-");
        d.getAssignment().addDeliverable(userList.get(0), d.getBody());
        when(deliverableRepository.findById(d.getId())).thenReturn(Optional.of(d));
        assignmentService.gradeDeliverable(0L, 0L, "91");
        Assertions.assertThat(d.getGrade()).isEqualTo("91");
    }    
}
