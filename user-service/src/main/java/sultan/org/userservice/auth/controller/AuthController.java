package sultan.org.userservice.auth.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sultan.org.userservice.auth.client.KeycloakClient;
import sultan.org.userservice.auth.dto.request.LoginRequestDto;
import sultan.org.userservice.auth.dto.request.RefreshTokenRequestDto;
import sultan.org.userservice.auth.dto.request.RegistrationRequestDto;
import sultan.org.userservice.auth.dto.response.JwtAuthenticationResponseDto;
import sultan.org.userservice.auth.service.AuthService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponseDto> register(
            @RequestBody RegistrationRequestDto registrationRequestDto){
        log.info(registrationRequestDto.toString());

        return ResponseEntity.ok(authService.createUser(registrationRequestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.loginUser(loginRequestDto));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponseDto> refreshToken(
            @RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDto));

    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        authService.logout(refreshTokenRequestDto);
        return ResponseEntity.noContent().build();

    }
}
