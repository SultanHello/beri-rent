package sultan.org.userservice.user.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
