package sultan.org.userservice.userprofile.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sultan.org.userservice.userprofile.model.dto.request.UserProfileRequestDto;
import sultan.org.userservice.userprofile.model.dto.response.UserProfileResponseDto;

@Service
public interface UserProfileService {

    void createUserProfile(UserProfileRequestDto userProfileRequestDto,String token);

    UserProfileResponseDto getMyProfile(String token);

    UserProfileResponseDto getProfileByUserId(Long id);
    void uploadAvatar(MultipartFile file, String token);
}
