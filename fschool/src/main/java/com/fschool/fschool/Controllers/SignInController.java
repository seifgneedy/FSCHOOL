package com.fschool.fschool.Controllers;
import java.util.Optional;

import javax.servlet.http.*;

import com.fschool.fschool.Model.Entities.User;
import com.fschool.fschool.Model.Services.AuthenticationService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = { "http://localhost:8080" })
@RestController
public class SignInController {
    final static int EIGHT_DAYS=8 * 24 * 60 * 60;
    @Autowired
    private AuthenticationService authentication;
    @PostMapping("/sign-in")
    public String signIn (@RequestBody String userCredentials,
                        HttpServletResponse response){
        System.out.println(userCredentials);
        JSONObject obj = new JSONObject(userCredentials);
		String email = obj.getString("Email");
		String password = obj.getString("Password");
        Optional<User> user = authentication.authenticate(email, password);
        if( !user.isPresent()){
            return "Incorrect Credentials";
        }
        Cookie cookie = new Cookie("id",Long.toString(user.get().getId()));
        cookie.setMaxAge(EIGHT_DAYS);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return user.get().getRole();
    }
}
