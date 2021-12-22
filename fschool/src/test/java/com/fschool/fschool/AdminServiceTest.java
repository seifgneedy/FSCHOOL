package com.fschool.fschool;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CourseRepository courseRepository;

    @Autowired
    @InjectMocks
    private AdminService adminService;

    private User user1;
    private User user2;
    private User user3;
    private List<User> userList = new ArrayList<>();

    private Course course1;
    private Course course2;
    private List<Course> courseList =  new ArrayList<>();;


    
    private void addUsers(){
        user1 = new User();
        user1.setEmail("a@gmail.com");
        user1.setRole("student");
        user1.setFirstName("ahmed");
        user1.setLastName("nagy");
        user1.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user1.setSex('m');
        user1.setPassword(DigestUtils.sha256Hex("password"));

        user2 = new User();
        user2.setEmail("b@gmail.com");
        user2.setRole("student");
        user2.setFirstName("chloe");
        user2.setLastName("todd");
        user2.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user2.setSex('f');
        user2.setPassword(DigestUtils.sha256Hex("pass"));

        
        user3 = new User();
        user3.setEmail("c@gmail.com");
        user3.setRole("teacher");
        user3.setFirstName("kate");
        user3.setLastName("smith");
        user3.setBirthDate(LocalDate.of(1987, Month.SEPTEMBER, 11));
        user3.setSex('f');
        user3.setPassword(DigestUtils.sha256Hex("NOW_pass"));

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

    }

    private void addCourses(){

        course1 = new Course();
        course1.setCode("CSE 123");
        course1.setName("Computer Vision");

        course2 = new Course();
        course2.setCode("CSE 321");
        course2.setName("Data Structure");

        courseList = new ArrayList<>();
        courseList.add(course1);
        courseList.add(course2);
    }

    @Test
    @Order(1)
    public void getStudentsTest(){
        addUsers();
        List<User> addedStudents = new ArrayList<>();
        for(User user : userList){
            if(user.getRole().equals("student"))
            addedStudents.add(user);
        }
        when(userRepository.findByRole("student")).thenReturn(addedStudents);
        List<User> students =  adminService.getStudents();
        Assertions.assertThat(students.size()).isEqualTo(addedStudents.size()).isEqualTo(2);
        boolean correctStudents = (students.get(0).getEmail().equals("a@gmail.com") && students.get(1).getEmail().equals("b@gmail.com"))
                                    || students.get(0).getEmail().equals("b@gmail.com") && students.get(1).getEmail().equals("a@gmail.com");
        Assertions.assertThat(correctStudents).isTrue();


    }

    @Test
    @Order(2)
    public void getStudentsNoAddTest(){
        List<User> addedStudents = new ArrayList<>();
        for(User user : userList){
            if(user.getRole().equals("student"))
            addedStudents.add(user);
        }
        when(userRepository.findByRole("student")).thenReturn(addedStudents);
        List<User> students =  adminService.getStudents();
        Assertions.assertThat(students.size()).isEqualTo(addedStudents.size()).isEqualTo(0);
    }

    @Test
    @Order(3)
    public void getTeachersTest(){
        addUsers();
        List<User> addedteachers = new ArrayList<>();
        for(User user : userList){
            if(user.getRole().equals("teacher"))
            addedteachers.add(user);
        }
        when(userRepository.findByRole("teacher")).thenReturn(addedteachers);
        List<User> students =  adminService.getTeachers();
        Assertions.assertThat(students.size()).isEqualTo(addedteachers.size()).isEqualTo(1);
    }


    @Test
    @Order(3)
    public void getTeachersNoAddTest(){
        List<User> addedTeachers = new ArrayList<>();
        for(User user : userList){
            if(user.getRole().equals("teacher"))
            addedTeachers.add(user);
        }
        when(userRepository.findByRole("teacher")).thenReturn(addedTeachers);
        List<User> students =  adminService.getTeachers();
        Assertions.assertThat(students.size()).isEqualTo(addedTeachers.size()).isEqualTo(0);
    }

    @Test
    @Order(5)
    public void getCoursesTest(){
        addCourses();
        when(courseRepository.findAll()).thenReturn(courseList);
        List<Course> courses =  adminService.getCourses();
        Assertions.assertThat(courses.size()).isEqualTo(courseList.size()).isEqualTo(2);
    }

    @Test
    @Order(6)
    public void getCoursesNoAddTest(){
        when(courseRepository.findAll()).thenReturn(courseList);
        List<Course> courses =  adminService.getCourses();
        Assertions.assertThat(courses.size()).isEqualTo(courseList.size()).isEqualTo(0);
    }

    @Test
    @Order(7)
    public void addUserTest(){
        addUsers();
        User user = userList.get(0);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(null));
        user.setId(1801001L);
        Assertions.assertThat(adminService.addUser(user)).isNotNull();
        verify(userRepository,times(1)).save(user);
    }

    @Test
    @Order(8)
    public void addExistingUserTest(){
        addUsers();
        User user = userList.get(0);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.ofNullable(user));
        Assertions.assertThat(adminService.addUser(user)).isNull();
        verify(userRepository,times(0)).save(any());

    }

    @Test
    @Order(9)
    public void changePasswordTest(){
        addUsers();
        User user = userList.get(0);
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(user));
        Assertions.assertThat(adminService.changePassword(1801001L, "totalyNewPassword")).isTrue();
        Assertions.assertThat(user.getPassword().equals(DigestUtils.sha256Hex("totalyNewPassword")));
        verify(userRepository,times(1)).save(user);
    }

    @Test
    @Order(10)
    public void changePasswordNewUserTest(){
        addUsers();
        when(userRepository.findById(125L)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThat(adminService.changePassword(125L, "totalyNewPassword")).isFalse();
        verify(userRepository,times(0)).save(any());
    }


    @Test
    @Order(11)
    public void addCourseTest(){
        addCourses();
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThat(adminService.addCourse(course.getCode(), course.getName())).isTrue();
        verify(courseRepository,times(1)).save(course);
    }

    @Test
    @Order(12)
    public void addExistingCourseTest(){
        addCourses();
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(course));
        Assertions.assertThat(adminService.addCourse(course.getCode(), course.getName())).isFalse();
        verify(courseRepository,times(0)).save(any());
    }

    @Test
    @Order(13)
    public void getCourseTest(){
        addCourses();
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(course));
        Assertions.assertThat(adminService.getCourse(course.getCode())).isNotNull();
    }

    @Test
    @Order(14)
    public void getNonExistingCourseTest(){
        addCourses();
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThat(adminService.getCourse(course.getCode())).isNull();
    }

    @Test
    @Order(15)
    public void addUserToCourseTest(){
        addUsers();
        addCourses();

        User user = userList.get(0);
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(user));
        
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(course));

        Assertions.assertThat(adminService.addUserToCourse(1801001L, course.getCode() ,user.getRole())).isNotNull();
        Assertions.assertThat(user.getCourses().size()).isEqualTo(1);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(1);
        
    }

    @Test
    @Order(16)
    public void addNonExistingUserToCourseTest(){
        addUsers();
        addCourses();

        User user = userList.get(0);
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(null));
        
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(course));

        Assertions.assertThat(adminService.addUserToCourse(1801001L, course.getCode(), user.getRole())).isNull();
        Assertions.assertThat(user.getCourses().size()).isEqualTo(0);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(0);
        
    }

    @Test
    @Order(17)
    public void addUserToNonExistingCourseTest(){
        addUsers();
        addCourses();

        User user = userList.get(0);
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(user));
        
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThat(adminService.addUserToCourse(1801001L, course.getCode(), user.getRole())).isNull();
        Assertions.assertThat(user.getCourses().size()).isEqualTo(0);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(0);
        
    }

    @Test
    @Order(18)
    public void removeUserFromCourseTest(){
        addUsers();
        addCourses();

        User user = userList.get(0);
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(user));
        
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(course));

        Assertions.assertThat(adminService.addUserToCourse(1801001L, course.getCode(), user.getRole())).isNotNull();
        Assertions.assertThat(user.getCourses().size()).isEqualTo(1);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(1);


        Assertions.assertThat(adminService.removeUserFromCourse(1801001L, course.getCode())).isNotNull();
        Assertions.assertThat(user.getCourses().size()).isEqualTo(0);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(0);
    }

    @Test
    @Order(19)
    public void removeNonAddedUserFromCourseTest(){
        addUsers();
        addCourses();

        User user = userList.get(0);
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(user));
        
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(course));

        Assertions.assertThat(user.getCourses().size()).isEqualTo(0);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(0);
        Assertions.assertThat(adminService.removeUserFromCourse(1801001L, course.getCode())).isFalse();
        Assertions.assertThat(user.getCourses().size()).isEqualTo(0);
        Assertions.assertThat(course.getMembers().size()).isEqualTo(0);
    }

    @Test
    @Order(20)
    public void deleteUserTest(){
        addUsers();
        User user = userList.get(0);
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(user));
        Assertions.assertThat(adminService.deleteUser(1801001L)).isTrue();
        verify(userRepository,times(1)).delete(user);
    }

    @Test
    @Order(21)
    public void deleteNonExistingUserTest(){
        when(userRepository.findById(1801001L)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThat(adminService.deleteUser(1801001L)).isFalse();
        verify(userRepository,times(0)).delete(any());
    }

    @Test
    @Order(22)
    public void deleteCourseTest(){
        addCourses();
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(course));
        Assertions.assertThat(adminService.deleteCourse(course.getCode())).isTrue();
        verify(courseRepository,times(1)).delete(course);
    }

    @Test
    @Order(23)
    public void deleteNonExistingCourseTest(){
        addCourses();
        Course course = courseList.get(0);
        when(courseRepository.findByCode(course.getCode())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThat(adminService.deleteCourse(course.getCode())).isFalse();
        verify(courseRepository,times(0)).delete(any());
    }
    
}
