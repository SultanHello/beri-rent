package sultan.org.userservice.auth.service;

import sultan.org.userservice.auth.dto.request.LoginRequestDto;
import sultan.org.userservice.auth.dto.request.RefreshTokenRequestDto;
import sultan.org.userservice.auth.dto.request.RegistrationRequestDto;
import sultan.org.userservice.auth.dto.response.JwtAuthenticationResponseDto;

public interface AuthService {
    JwtAuthenticationResponseDto loginUser(LoginRequestDto loginRequestDto);
    JwtAuthenticationResponseDto createUser(RegistrationRequestDto dto);
    JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshToken);
    void logout(RefreshTokenRequestDto refreshToken);


}
