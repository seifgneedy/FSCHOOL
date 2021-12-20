package com.fschool.fschool.Controllers;
import javax.servlet.http.*;

import com.fschool.fschool.Model.Services.Authentication;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
public class SignInController {
    final static int EIGHT_DAYS=8 * 24 * 60 * 60;
    @Autowired
    private Authentication authentication;
    @PostMapping("/sign-in")
    public boolean signIn (@RequestBody String userCredentials,
                        HttpServletResponse response){

        JSONObject obj = new JSONObject(userCredentials);
		String id = obj.getString("id");
		String password = obj.getString("password");
		String role = obj.getString("role");

        if(!authentication.authenticate(Long.valueOf(id), password, role))
            return false;
        Cookie cookie = new Cookie("id",id);
        cookie.setMaxAge(EIGHT_DAYS);
        cookie.setSecure(true);
        System.out.println("entered id " + id);
        response.addCookie(cookie);
        return true;
    }
}
