package sultan.org.userservice.user.service;

import jakarta.persistence.Lob;
import sultan.org.userservice.ratingsummary.model.dto.response.RatingSummaryResponseDto;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;
import sultan.org.userservice.user.model.dto.UserDto;
import sultan.org.userservice.user.model.entity.User;

import java.util.UUID;

public interface UserService {
    UserDto save(User user);

    User findUserByKeycloakId(UUID keyCloakId);

    User findUserById(Long id);

    User me(String token);
}
