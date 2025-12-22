package sultan.org.userservice.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.userservice.auth.client.KeycloakClient;
import sultan.org.userservice.auth.dto.request.LoginRequestDto;
import sultan.org.userservice.auth.dto.request.RefreshTokenRequestDto;
import sultan.org.userservice.auth.dto.request.RegistrationRequestDto;
import sultan.org.userservice.auth.dto.response.JwtAuthenticationResponseDto;
import sultan.org.userservice.auth.service.AuthService;
import sultan.org.userservice.common.util.JwtUtil;
import sultan.org.userservice.user.service.UserRegistrationAsyncProcessor;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final KeycloakClient keycloakClient;
    private final UserRegistrationAsyncProcessor asyncProcessor;

    @Override
    public JwtAuthenticationResponseDto loginUser(LoginRequestDto loginRequestDto) {
        return keycloakClient.loginUser(loginRequestDto);
    }

    @Override
    public JwtAuthenticationResponseDto createUser(RegistrationRequestDto dto) {
        JwtAuthenticationResponseDto  jwt=keycloakClient.createUser(dto);
        UUID keyCloakUserUd = JwtUtil.extractSubject(jwt.getAccessToken());
        asyncProcessor.saveUserAsync(dto,keyCloakUserUd);
        return jwt;
    }

    @Override
    public JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshToken) {
        return keycloakClient.refreshToken(refreshToken);
    }

    @Override
    public void logout(RefreshTokenRequestDto refreshToken) {
        keycloakClient.logoutUser(refreshToken);

    }
}
