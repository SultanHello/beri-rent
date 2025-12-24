package sultan.org.userservice.userprofile.service;

import org.springframework.stereotype.Service;
import sultan.org.userservice.userprofile.model.dto.request.UserProfileRequestDto;
import sultan.org.userservice.userprofile.model.dto.response.UserProfileResponseDto;

@Service
public interface UserProfileService {

    void createUserProfile(UserProfileRequestDto userProfileRequestDto,String token);

    UserProfileResponseDto getMyProfile(String token);

    UserProfileResponseDto getProfileById(Long id);
}
