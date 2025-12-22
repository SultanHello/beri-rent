package sultan.org.userservice.user.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import sultan.org.userservice.user.model.dto.UserDto;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.repository.UserRepository;
import sultan.org.userservice.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserDto save(User user) {
        User user1  = userRepository.save(user);
        return buildUser(user1);
    }

    private static UserDto buildUser(User user1) {
        return UserDto.builder()
                .email(user1.getEmail())
                .createdAt(user1.getCreatedAt())
                .keycloakId(user1.getKeycloakId())
                .createdAt(user1.getCreatedAt())
                .updateAt(user1.getUpdatedAt())
                .username(user1.getUsername())
                .build();
    }
}
