package com.fschool.fschool.Controllers;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
public class SignInController {
    
    @PostMapping("/sign-in")
    public boolean signIn (@RequestParam String id,
                        @RequestParam String password,
                        HttpServletResponse response){
        // Authinticate here
        ////////////
        ///////////
        ////////////
        Cookie cookie = new Cookie("id", id);
        cookie.setMaxAge(8 * 24 * 60 * 60); // 8 days
        response.addCookie(cookie);
        return true;
    }
}
