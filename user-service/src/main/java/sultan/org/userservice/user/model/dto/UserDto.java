package sultan.org.userservice.user.model.dto;

import lombok.Builder;
import lombok.Data;

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
}
