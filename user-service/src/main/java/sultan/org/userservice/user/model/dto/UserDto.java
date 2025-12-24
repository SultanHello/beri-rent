package sultan.org.userservice.user.model.dto;

import lombok.Builder;
import lombok.Data;
import sultan.org.userservice.user.model.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;



@Builder
@Data
public class UserDto {
    private UUID keycloakId;
    private String email;
    private String password;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;


    public static UserDto fromEntity(User user){
        return UserDto.builder()

                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .keycloakId(user.getKeycloakId())
                .createdAt(user.getCreatedAt())
                .updateAt(user.getUpdatedAt())
                .username(user.getUsername())
                .build();
    }
}
