package sultan.org.userservice.auth.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sultan.org.userservice.auth.config.SecurityProperties;
import sultan.org.userservice.auth.dto.request.LoginRequestDto;
import sultan.org.userservice.auth.dto.request.RefreshTokenRequestDto;
import sultan.org.userservice.auth.dto.request.RegistrationRequestDto;
import sultan.org.userservice.auth.dto.response.AdminTokenResponseDto;
import sultan.org.userservice.auth.dto.response.JwtAuthenticationResponseDto;
import sultan.org.userservice.auth.dto.response.KeycloakRoleResponseDto;
import sultan.org.userservice.auth.exception.LogoutException;
import sultan.org.userservice.auth.exception.RegistrationException;
import sultan.org.userservice.auth.exception.TokenRefreshException;
import sultan.org.userservice.auth.util.KeycloakAdminTokenProvider;
import sultan.org.userservice.auth.util.KeycloakRoleProvider;
import sultan.org.userservice.user.model.dto.request.UpdateUserRequestDto;
import sultan.org.userservice.user.model.enums.UserRole;

import javax.security.auth.login.LoginException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Component
@RequiredArgsConstructor
public class KeycloakClient {

    private final WebClient keycloakWebClient;
    private final SecurityProperties securityProperties;
    private final KeycloakAdminTokenProvider keycloakAdminTokenProvider;
    private final KeycloakRoleProvider keycloakRoleProvider;

    public JwtAuthenticationResponseDto createUser(RegistrationRequestDto dto) {
        // Получаем access token от АДМИНА
        String accessToken = keycloakAdminTokenProvider.getAdminAccessToken().getAccessToken();

        // Формирование payload
        Map<String, Object> userPayload = Map.of(
                "email", dto.getEmail(),
                "username", dto.getUsername(),
                "enabled", true,
                "emailVerified", true,
                "credentials", Collections.singletonList(
                        Map.of("type", "password", "value", dto.getPassword(), "temporary", false)
                )
        );
        System.out.println(accessToken);





        // Запрос на создание User
        String userId = keycloakWebClient.post()
                .uri(securityProperties.getBaseUrl() + "/admin/realms/" + securityProperties.getRealm() + "/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPayload)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RegistrationException("User creation failed: " + body)))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RegistrationException("Server error: " + body)))
                )
                .toBodilessEntity()
                .map(responseEntity -> {
                    String locationHeader = responseEntity.getHeaders().getFirst(HttpHeaders.LOCATION);
                    if (locationHeader != null && locationHeader.contains("/users/")) {
                        return locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
                    }
                    throw new RegistrationException("Location header missing");
                })
                .block();

        // Получение роли
        KeycloakRoleResponseDto keycloakRoleResponseDto = keycloakRoleProvider.getRole(UserRole.USER);

        // Присваивание роли
        keycloakRoleProvider.assignRole(keycloakRoleResponseDto, userId, accessToken);

        // Логин, чтобы сразу получить jwt tokens
        return loginUser(new LoginRequestDto(dto.getEmail(), dto.getPassword()));
    }



    public JwtAuthenticationResponseDto loginUser(LoginRequestDto loginRequestDto) {
        return keycloakWebClient.post()
                .uri("/realms/" + securityProperties.getRealm() + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", "password")
                        .with("client_id", securityProperties.getClientUser().getId())
                        .with("client_secret", securityProperties.getClientUser().getSecret())
                        .with("username", loginRequestDto.getEmail())
                        .with("password", loginRequestDto.getPassword())
                )
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new LoginException(
                                        "Keycloak login failed: " + response.statusCode() + " - " + body))
                )
                .bodyToMono(JwtAuthenticationResponseDto.class)
                .block();
    }

    public JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        return keycloakWebClient.post()
                .uri("/realms/" + securityProperties.getRealm() + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", "refresh_token")
                        .with("client_id", securityProperties.getClientUser().getId())
                        .with("client_secret", securityProperties.getClientUser().getSecret())
                        .with("refresh_token", refreshTokenRequestDto.getRefreshToken())
                )
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new TokenRefreshException(
                                        "Keycloak token refresh failed: " + response.statusCode() + " - " + body))
                )
                .bodyToMono(JwtAuthenticationResponseDto.class)
                .block();
    }

    public void logoutUser(RefreshTokenRequestDto refreshTokenRequestDto) {
        keycloakWebClient.post()
                .uri("/realms/" + securityProperties.getRealm() + "/protocol/openid-connect/logout")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("client_id", securityProperties.getClientUser().getId())
                        .with("client_secret", securityProperties.getClientUser().getSecret())
                        .with("refresh_token", refreshTokenRequestDto.getRefreshToken())
                )
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new LogoutException(
                                        "Keycloak logout failed: " + response.statusCode() + " - " + body))
                )
                .toBodilessEntity()
                .block();
    }

//    public void updateUser(UUID keycloakId, UpdateUserRequestDto dto) {
//        AdminTokenResponseDto tokenResponseDto = keycloakAdminTokenProvider.getAdminAccessToken();
//        Map<String, Object> payload = Map.of(
//                "firstName", dto.getFirstName(),
//                "lastName", dto.getLastName(),
//                "email", dto.getEmail(),
//                "emailVerified", true,
//                "attributes", Map.of("phoneNumber", List.of(dto.getPhoneNumber()))
//        );
//
//        keycloakWebClient.put()
//                .uri("/admin/realms/" + securityProperties.getRealm() + "/users/" + keycloakId)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponseDto.getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(payload)
//                .retrieve()
//                .onStatus(
//                        status -> status.is4xxClientError() || status.is5xxServerError(),
//                        response -> response.bodyToMono(String.class)
//                                .map(body -> new RegistrationException("Keycloak update failed: " + response.statusCode() + " - " + body))
//                )
//                .toBodilessEntity()
//                .block();
//    }

    public void deactivateUser(UUID keycloakId) {
        Map<String, Object> payload = Map.of("enabled", false);

        keycloakWebClient.put()
                .uri("/admin/realms/" + securityProperties.getRealm() + "/users/" + keycloakId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakAdminTokenProvider.getAdminAccessToken().getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RegistrationException("Keycloak deactivation failed: " + response.statusCode() + " - " + body))
                )
                .toBodilessEntity()
                .block();
    }

    public void activateUser(UUID keycloakId) {
        Map<String, Object> payload = Map.of("enabled", true);

        keycloakWebClient.put()
                .uri("/admin/realms/" + securityProperties.getRealm() + "/users/" + keycloakId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloakAdminTokenProvider.getAdminAccessToken().getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RegistrationException("Keycloak activation failed: " + response.statusCode() + " - " + body))
                )
                .toBodilessEntity()
                .block();
    }
}