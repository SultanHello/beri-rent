package sultan.org.userservice.user.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {

    private String email;
    private String username;
    private String phoneNumber;

}