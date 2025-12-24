package sultan.org.userservice.userProfileTest.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import sultan.org.userservice.user.exceptions.UserAlreadyExistsException;
import sultan.org.userservice.user.model.dto.UserDto;
import sultan.org.userservice.user.exceptions.UserNotFoundException;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.repository.UserRepository;
import sultan.org.userservice.user.service.impl.UserServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;
    private User user;
    private UUID keycloakId;
    @BeforeEach
    void setUp() {
        keycloakId = UUID.randomUUID();

        user = User.builder()
                .email("test@mail.com")
                .username("testuser")
                .keycloakId(keycloakId)
                .build();
    }

    @Test
    void saveSuccess(){
        when(userRepository.save(user)).thenReturn(user);
        UserDto userDto = userService.save(user);
        assertNotNull(userDto);
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getUsername(), userDto.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void save_emailAlreadyExists_throwsException(){
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class,()->userService.save(user));
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @Test
    void save_dataIntegrityViolation_otherReason() {
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        assertThrows(DataIntegrityViolationException.class,()->userService.save(user));
    }

    @Test
    void successFindByKeyCloakId(){
        when(userRepository.findByKeycloakId(keycloakId)).thenReturn(Optional.of(user));
        User foundUser = userService.findUserByKeycloakId(keycloakId);
        assertEquals(foundUser.getId(),user.getId());
        verify(userRepository).findByKeycloakId(keycloakId);
    }

    @Test
    void notFoundFindByKeyCloakId(){
        when(userRepository.findByKeycloakId(keycloakId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.findUserByKeycloakId(keycloakId));
        verify(userRepository).findByKeycloakId(keycloakId);

    }

}
//@Override
//public User findUserByKeycloakId(UUID keyCloakId) {
//    return userRepository.findByKeycloakId(keyCloakId).orElseThrow(()->new UserNotFoundException(
//            "User not found with keycloakId: " + keyCloakId
//    ));
//
//}

