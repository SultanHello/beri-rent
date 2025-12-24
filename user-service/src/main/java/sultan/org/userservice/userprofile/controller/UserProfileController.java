package sultan.org.userservice.userprofile.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sultan.org.userservice.userprofile.model.dto.request.UserProfileRequestDto;
import sultan.org.userservice.userprofile.model.dto.response.UserProfileResponseDto;
import sultan.org.userservice.userprofile.model.entity.UserProfile;
import sultan.org.userservice.userprofile.service.UserProfileService;

@RestController
@RequestMapping("/api/user-profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @PostMapping("/create")
    public void create(@RequestHeader("Authorization") String token, @RequestBody UserProfileRequestDto userProfileRequestDto){
        userProfileService.createUserProfile(userProfileRequestDto,token);
    }
    @GetMapping("/my")
    public ResponseEntity<UserProfileResponseDto> getMyUserProfile(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userProfileService.getMyProfile(token));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(@PathVariable Long id){
        return ResponseEntity.ok(userProfileService.getProfileById(id));
    }



}
