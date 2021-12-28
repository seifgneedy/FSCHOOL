package com.fschool.fschool;

import static org.mockito.Mockito.when;

import java.time.*;
import java.util.*;

import com.fschool.fschool.Model.Entities.User;
import com.fschool.fschool.Model.Repositories.UserRepository;
import com.fschool.fschool.Model.Services.AuthenticationService;

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
    private AuthenticationService authentication;

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
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.ofNullable(user1));
        Optional<User> user = authentication.authenticate("a@gmail.com", "password");
        Assertions.assertThat(user.isPresent()).isTrue();
        Assertions.assertThat(user.get()).isEqualTo(user1);
    }

    @Test
    @Order(2)
    public void signInTest2(){
        when(userRepository.findByEmail("b@gmail.com")).thenReturn(Optional.ofNullable(user2));
        Optional<User> user = authentication.authenticate("b@gmail.com", "pass");
        Assertions.assertThat(user.isPresent()).isTrue();
        Assertions.assertThat(user.get()).isEqualTo(user2);
    }

    @Test
    @Order(3)
    public void wrongPasswordTest(){
        when(userRepository.findByEmail("a@gmail.com")).thenReturn(Optional.ofNullable(user1));
        Optional<User> user = authentication.authenticate("a@gmail.com", "pass");
        Assertions.assertThat(user.isPresent()).isFalse();
    }



    @Test
    @Order(4)
    public void wrongEmailTest(){
        when(userRepository.findByEmail("Abcd@gmail.com")).thenReturn(Optional.ofNullable(null));
        Optional<User> user = authentication.authenticate("Abcd@gmail.com", "password");
        Assertions.assertThat(user.isPresent()).isFalse();
    }
    


    
}
