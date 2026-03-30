package sultan.org.userservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sultan.org.userservice.auth.dto.request.RegistrationRequestDto;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;
import sultan.org.userservice.user.model.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRegistrationAsyncProcessor {
    private final UserService userService;

    @Async
    @Transactional
    public void saveUserAsync(RegistrationRequestDto dto, UUID keycloakId) {
        User user = User.builder()
                .email(dto.getEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .username(dto.getUsername())
                .keycloakId(keycloakId)
                .ratingSummary(new RatingSummary())
                .build();
        System.out.println("do sohronenie : "+ keycloakId);
        userService.save(user);
    }
}