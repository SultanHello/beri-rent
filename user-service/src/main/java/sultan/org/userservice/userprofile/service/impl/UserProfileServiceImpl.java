package sultan.org.userservice.userprofile.service.impl;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sultan.org.userservice.common.util.JwtUtil;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.repository.UserRepository;
import sultan.org.userservice.user.service.UserService;
import sultan.org.userservice.userprofile.exceptions.UserProfileNotFoundException;
import sultan.org.userservice.userprofile.model.dto.request.UserProfileRequestDto;
import sultan.org.userservice.userprofile.model.dto.response.UserProfileResponseDto;
import sultan.org.userservice.userprofile.model.entity.UserProfile;
import sultan.org.userservice.userprofile.repository.UserProfileRepository;
import sultan.org.userservice.userprofile.service.UserProfileService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    @Override
    public void createUserProfile(UserProfileRequestDto userProfileRequestDto,String token) {
        UUID keycloakId = jwtUtil.extractSubject(token);
        User user = userService.findUserByKeycloakId(keycloakId);
        UserProfile userProfile = getBuildUserProfile(userProfileRequestDto, user);
        Optional.ofNullable(user.getUserProfile()).ifPresent(profile->userProfile.setId(profile.getId()));
        userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfileResponseDto getMyProfile(String token) {
        UUID keycloakId = jwtUtil.extractSubject(token);
        User user = userService.findUserByKeycloakId(keycloakId);
        return UserProfileResponseDto.fromEntity(user.getUserProfile());
    }

    @Override
    public UserProfileResponseDto getProfileById(Long id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(
                ()->new UserProfileNotFoundException("user profile not exist with this id"));
        return UserProfileResponseDto.fromEntity(userProfile);
    }

    private static UserProfile getBuildUserProfile(UserProfileRequestDto userProfileRequestDto, User user) {

        return UserProfile.builder()
                .user(user)
                .gender(userProfileRequestDto.getGender())
                .lastName(userProfileRequestDto.getLastName())
                .firstName(userProfileRequestDto.getFirstName())
                .aboutMe(userProfileRequestDto.getAboutMe())
                .updatedAt(userProfileRequestDto.getUpdatedAt())
                .avatarUrl(userProfileRequestDto.getAvatarUrl())
                .location(userProfileRequestDto.getLocation())
                .build();
    }
}
