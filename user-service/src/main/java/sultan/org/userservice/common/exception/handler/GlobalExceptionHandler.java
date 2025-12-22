package sultan.org.userservice.common.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import sultan.org.userservice.auth.exception.*;
import sultan.org.userservice.common.model.dto.ErrorResponseDto;
import sultan.org.userservice.common.util.ErrorResponseUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnhandledExceptions(Exception e, WebRequest webRequest){
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e,webRequest, HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AdminTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleAdminTokenException(Exception e,WebRequest webRequest){
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e,webRequest,HttpStatus.UNAUTHORIZED),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(LogoutException.class)
    public ResponseEntity<ErrorResponseDto> handleLogoutException(Exception e,WebRequest webRequest){
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e,webRequest,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponseDto> handleRegistrationException(Exception e,WebRequest webRequest){
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e,webRequest,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoleAssignmentException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleAssigmentException(Exception e,WebRequest webRequest){
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e,webRequest,HttpStatus.UNAUTHORIZED),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRoleNotFoundException(Exception e,WebRequest webRequest){
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e,webRequest,HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ErrorResponseDto> handleTokenRefreshException(Exception e,WebRequest webRequest){
        return new ResponseEntity<>(ErrorResponseUtil.buildErrorResponse(e,webRequest,HttpStatus.UNAUTHORIZED),HttpStatus.UNAUTHORIZED);
    }
}
