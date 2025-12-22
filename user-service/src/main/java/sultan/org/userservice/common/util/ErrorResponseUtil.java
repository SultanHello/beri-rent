package sultan.org.userservice.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;
import sultan.org.userservice.common.model.dto.ErrorResponseDto;

import java.time.LocalDateTime;

public class ErrorResponseUtil {

    public static ErrorResponseDto buildErrorResponse(Exception e, WebRequest request, HttpStatus status) {
        return new ErrorResponseDto(
                e.getMessage(),                        // сообщение ошибки
                request.getDescription(false),         // путь запроса
                status.value(),                        // HTTP статус
                LocalDateTime.now()                    // время ошибки
        );
    }
}

