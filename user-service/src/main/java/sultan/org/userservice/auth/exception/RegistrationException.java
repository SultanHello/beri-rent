package sultan.org.userservice.auth.exception;

import org.springframework.http.ResponseEntity;

public class RegistrationException extends RuntimeException{
    public RegistrationException(String message){
        super(message);
    }

}
