package sultan.org.userservice.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sultan.org.userservice.user.model.dto.UserDto;
import sultan.org.userservice.user.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class UserInternalController {
    private final UserService userService;
    @GetMapping("/userId")
    public UUID getUserId(@RequestHeader("Authorization") String token){
        return userService.getUserId(token);
    }
}
