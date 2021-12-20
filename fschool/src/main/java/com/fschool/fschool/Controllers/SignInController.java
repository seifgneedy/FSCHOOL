package com.fschool.fschool.Controllers;
import javax.servlet.http.*;

import com.fschool.fschool.Model.Services.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
public class SignInController {
    final static int EIGHT_DAYS=8 * 24 * 60 * 60;
    @Autowired
    private Authentication authentication;
    @PostMapping("/sign-in")
    public boolean signIn (@RequestParam String id,
                        @RequestParam String password,
                        @RequestParam String role,
                        HttpServletResponse response){
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
