package com.fschool.fschool.Controllers;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
public class SignInController {
    
    @GetMapping("/sign-in")
    public boolean signIn (@RequestParam String id,
                        @RequestParam String password,
                        @RequestParam String role,
                        HttpServletResponse response){
        // Authinticate here
        ////////////
        ///////////
        ////////////
        Cookie cookie = new Cookie("id", id);
        cookie.setMaxAge(8 * 24 * 60 * 60); // 8 days
        System.out.println("entered id " + id);
        response.addCookie(cookie);
        return true;
    }
}
