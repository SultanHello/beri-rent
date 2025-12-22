package sultan.org.userservice.user.service;

import sultan.org.userservice.user.model.dto.UserDto;
import sultan.org.userservice.user.model.entity.User;

public interface UserService {
    UserDto save(User user);

}
