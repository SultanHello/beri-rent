package sultan.org.userservice.userprofile.model.dto.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.model.enums.Gender;
import sultan.org.userservice.userprofile.model.entity.Location;

import java.time.LocalDateTime;


@Getter
@Setter
public class UserProfileRequestDto {

    private Long id;

    private User user;
    private String firstName;
    private String lastName;
    private String avatarUrl;

    private Gender gender;
    private String aboutMe;
    private Location location;
    private LocalDateTime updatedAt;
}
