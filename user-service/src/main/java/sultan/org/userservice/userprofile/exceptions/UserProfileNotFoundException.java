package sultan.org.userservice.userprofile.exceptions;

import sultan.org.userservice.userprofile.model.entity.UserProfile;

public class UserProfileNotFoundException extends RuntimeException{
    public UserProfileNotFoundException(String message){
        super(message);
    }
}
