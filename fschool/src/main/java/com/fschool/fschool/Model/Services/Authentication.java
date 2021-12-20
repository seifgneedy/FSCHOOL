package com.fschool.fschool.Model.Services;

import java.util.Optional;

import com.fschool.fschool.Model.Entities.User;
import com.fschool.fschool.Model.Repositories.UserRepository;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class Authentication {
    private UserRepository userRepository;
    @Autowired
    Authentication(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public boolean authenticate(Long id,String password,String role){
        String hashedPassword=hashPassword(password);
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()
            && user.get().getHashedPassword().equals(hashedPassword)
            && user.get().getRole().equals(role))
                return true;
        return false;
    }
    private String hashPassword(String password){
        return DigestUtils.sha256Hex(password);
    }
    public static void main(String[] args) {
        System.out.println(DigestUtils.sha256Hex("turkish coffee"));
    }
}
