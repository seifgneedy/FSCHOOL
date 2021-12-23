package com.fschool.fschool;

import static org.mockito.Mockito.when;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.User;
import com.fschool.fschool.Model.Repositories.UserRepository;
import com.fschool.fschool.Model.Services.Authentication;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthinticationTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private Authentication authentication;

    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    private void createUsers(){
        user1 = new User();
        user1.setEmail("a@gmail.com");
        user1.setRole("student");
        user1.setFirstName("ahmed");
        user1.setLastName("nagy");
        user1.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user1.setSex("m");
        user1.setPassword(DigestUtils.sha256Hex("password"));

        user2 = new User();
        user2.setEmail("b@gmail.com");
        user2.setRole("student");
        user2.setFirstName("chloe");
        user2.setLastName("todd");
        user2.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user2.setSex("f");
        user2.setPassword(DigestUtils.sha256Hex("pass"));

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
    }

    @AfterEach
    public void deleteUsers(){
        user1 = null;
        user2 = null;
        userList = null;
    }
    
    @Test
    @Order(1)
    @Rollback(value = false)
    public void signInTest(){
        when(userRepository.findById(18010001L)).thenReturn(Optional.ofNullable(user1));
        boolean singedIn = authentication.authenticate(18010001L, "password", "student");
        Assertions.assertThat(singedIn).isTrue();
    }

    @Test
    @Order(2)
    public void signInTest2(){
        when(userRepository.findById(18010002L)).thenReturn(Optional.ofNullable(user2));
        boolean singedIn = authentication.authenticate(18010002L, "pass", "student");
        Assertions.assertThat(singedIn).isTrue();
    }

    @Test
    @Order(3)
    public void wrongPasswordTest(){
        when(userRepository.findById(18010001L)).thenReturn(Optional.ofNullable(user1));
        boolean singedIn = authentication.authenticate(18010001L, "pass", "student");
        Assertions.assertThat(singedIn).isFalse();
    }

    @Test
    @Order(4)
    public void wrongRoleTest(){
        when(userRepository.findById(18010001L)).thenReturn(Optional.ofNullable(user1));
        boolean singedIn = authentication.authenticate(18010001L, "password", "teacher");
        Assertions.assertThat(singedIn).isFalse();
    }

    @Test
    @Order(5)
    public void wrongIdTest(){
        when(userRepository.findById(0L)).thenReturn(Optional.ofNullable(null));
        boolean singedIn = authentication.authenticate(0L, "password", "teacher");
        Assertions.assertThat(singedIn).isFalse();
    }
    


    
}
