package sultan.org.userservice.user.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sultan.org.userservice.common.util.JwtUtil;
import sultan.org.userservice.user.exceptions.UserAlreadyExistsException;
import sultan.org.userservice.user.model.dto.UserDto;
import sultan.org.userservice.user.exceptions.UserNotFoundException;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.repository.UserRepository;
import sultan.org.userservice.user.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    public UserDto save(User user) {
        try {
            User savedUser = userRepository.save(user);
            return UserDto.fromEntity(savedUser);
        }catch (DataIntegrityViolationException e){
            if(userRepository.existsByEmail(user.getEmail())){
                throw new UserAlreadyExistsException("Email taken");
            }
            throw e;
        }

    }

    @Override
    public User findUserByKeycloakId(UUID keyCloakId) {
        System.out.println(userRepository.findAll().stream().map(a->a.getKeycloakId()).toList());
        return userRepository.findByKeycloakId(keyCloakId).orElseThrow(()->new UserNotFoundException(
                "User not found with keycloakId: " + keyCloakId
        ));

    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException("user not found"));
    }

    @Override
    public User me(String token) {
        UUID keycloakId = jwtUtil.extractSubject(token);
        User user = this.findUserByKeycloakId(keycloakId);
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.convertToDtoList(userRepository.findAll());
    }

    @Override
    public UUID getUserId(String token) {
        return jwtUtil.extractSubject(token);
    }

    private List<UserDto> convertToDtoList(List<User> users){
        return users.stream().map(UserDto::fromEntity).toList();

    }
}
