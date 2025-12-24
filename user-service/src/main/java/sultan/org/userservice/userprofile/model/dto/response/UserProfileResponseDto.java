package sultan.org.userservice.userprofile.model.dto.response;

import lombok.*;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.model.enums.Gender;
import sultan.org.userservice.userprofile.model.entity.Location;
import sultan.org.userservice.userprofile.model.entity.UserProfile;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder

public class UserProfileResponseDto {
    private Long id;

    private User user;
    private String firstName;
    private String lastName;
    private String avatarUrl;

    private Gender gender;
    private String aboutMe;
    private Location location;
    private LocalDateTime updatedAt;
    public static UserProfileResponseDto fromEntity(UserProfile userProfile) {
        return UserProfileResponseDto.builder()
                .id(userProfile.getId())
                .user(userProfile.getUser())
                .aboutMe(userProfile.getAboutMe())
                .location(userProfile.getLocation())
                .avatarUrl(userProfile.getAvatarUrl())
                .lastName(userProfile.getLastName())
                .firstName(userProfile.getFirstName())
                .updatedAt(userProfile.getUpdatedAt())
                .build();
    }
}
