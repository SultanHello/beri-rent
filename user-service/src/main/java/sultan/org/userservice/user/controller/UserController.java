package sultan.org.userservice.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sultan.org.userservice.user.model.dto.UserDto;
import sultan.org.userservice.user.service.UserService;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/me")
    public UserDto me(@RequestHeader("Authorization") String token){
        return UserDto.fromEntity(userService.me(token));
    }
    @GetMapping("/user-id/{id}")
    public UserDto findUser(@PathVariable Long id){
        return UserDto.fromEntity(userService.findUserById(id));
    }





}
