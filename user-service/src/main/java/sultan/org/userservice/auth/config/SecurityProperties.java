package sultan.org.userservice.auth.config;


import ch.qos.logback.core.net.server.Client;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import sultan.org.userservice.auth.dto.response.AdminTokenResponseDto;


@Data
@Component
@ConfigurationProperties("keycloak")
public class SecurityProperties {
    private String baseUrl;
    private String realm;
    private ClientUser clientUser;
    private ClientAdmin clientAdmin;
    @Data
    public static class ClientUser {
        private String id;
        private String secret;
    }
    @Data
    public static class ClientAdmin{
        private String id;
        private String secret;

    }



}
