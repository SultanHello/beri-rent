package sultan.org.userservice.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminTokenResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
}
