package sultan.org.userservice.common.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ErrorResponseDto {
    private String message;
    private String path;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponseDto(String message, String path, int status, LocalDateTime timestamp) {
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
    }

    // геттеры и сеттеры
}
