package com.fschool.fschool;

import com.fschool.fschool.Model.Repositories.UserRepository;
import com.fschool.fschool.Model.Entities.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void sizeTest() {
        List<User> list = userRepository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(userRepository.count());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void addOneUserTest() {
        User user = new User();
        user.setEmail("a@gmail.com");
        user.setRole("student");
        user.setFirstName("ahmed");
        user.setLastName("nagy");
        user.setBirthDate(LocalDate.of(1997, Month.JANUARY, 19));
        user.setSex('m');
        user.setPassword(DigestUtils.sha256Hex("password"));
        userRepository.save(user);
        Assertions.assertThat(user.getId()).isEqualTo(18010001L);
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void addAnotherUserTest() {
        User user = new User();
        user.setEmail("b@gmail.com");
        user.setRole("student");
        user.setFirstName("chloe");
        user.setLastName("todd");
        user.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user.setSex('f');
        user.setPassword(DigestUtils.sha256Hex("pass"));
        userRepository.save(user);
        Assertions.assertThat(userRepository.count()).isEqualTo(2L);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateUserTest() {
        User user = userRepository.findByEmail("b@gmail.com").get();
        user.setEmail("ab.1@gmail.com");
        User updatedUser = userRepository.save(user);
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("ab.1@gmail.com");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserTest() {
        User user = userRepository.findByEmail("ab.1@gmail.com").get();
        String email = user.getEmail();
        userRepository.delete(user);
        Optional<User> isFound = userRepository.findByEmail(email);
        Assertions.assertThat(isFound.isPresent()).isFalse();
    }

    @Test
    @Order(6)
    public void newSizeTest(){
        Assertions.assertThat(userRepository.count()).isEqualTo(1L);
    }

    @Test
    @Order(7)
    public void nonExistingIdTest(){
        Optional<User> user = userRepository.findById(125L);
        Assertions.assertThat(user.isPresent()).isFalse();
    }

    @Test
    @Order(8)
    @Rollback(value = false)
    public void addAfterDeletionTest(){
        Optional<User> deletedUser = userRepository.findByEmail("ab.1@gmail.com");
        Assertions.assertThat(deletedUser.isPresent()).isFalse();
        User user = new User();
        user.setEmail("ab.1@gmail.com");
        user.setRole("student");
        user.setFirstName("chloe");
        user.setLastName("todd");
        user.setBirthDate(LocalDate.of(1993, Month.APRIL, 11));
        user.setSex('f');
        user.setPassword(DigestUtils.sha256Hex("pass"));
        userRepository.save(user);
        Assertions.assertThat(userRepository.findByEmail("ab.1@gmail.com").isPresent()).isTrue();
        }

}
