package com.fschool.fschool.Controllers;
import javax.servlet.http.*;

import com.fschool.fschool.Model.Services.Authentication;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = { "http://localhost:8080" })
@RestController
public class SignInController {
    final static int EIGHT_DAYS=8 * 24 * 60 * 60;
    @Autowired
    private Authentication authentication;
    @PostMapping("/sign-in")
    public boolean signIn (@RequestBody String userCredentials,
                        HttpServletResponse response){
        System.out.println(userCredentials);
        JSONObject obj = new JSONObject(userCredentials);
		String id = obj.getString("ID");
		String password = obj.getString("Password");
		String role = obj.getString("Role");

        if(!authentication.authenticate(Long.valueOf(id), password, role)){
            System.out.println("Not exists");
            return false;
        }
        Cookie cookie = new Cookie("id",id);
        cookie.setMaxAge(EIGHT_DAYS);
        cookie.setSecure(true);
        System.out.println("entered id " + id);
        response.addCookie(cookie);
        return true;
    }
}
