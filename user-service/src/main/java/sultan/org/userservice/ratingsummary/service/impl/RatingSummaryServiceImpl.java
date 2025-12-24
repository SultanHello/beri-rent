package sultan.org.userservice.ratingsummary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.userservice.common.util.JwtUtil;
import sultan.org.userservice.ratingsummary.model.dto.response.RatingSummaryResponseDto;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;
import sultan.org.userservice.ratingsummary.service.RatingSummaryService;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingSummaryServiceImpl implements RatingSummaryService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Override
    public RatingSummaryResponseDto getMyRatingSummary(String token) {
        UUID keuCloakId = jwtUtil.extractSubject(token);
        User user = userService.findUserByKeycloakId(keuCloakId);
        RatingSummary ratingSummary = user.getRatingSummary();
        return RatingSummaryResponseDto.fromEntity(ratingSummary);
    }

    @Override
    public RatingSummaryResponseDto getUserRatingSummaryById(Long id) {
        User user = userService.findUserById(id);
        RatingSummary ratingSummary = user.getRatingSummary();
        return RatingSummaryResponseDto.fromEntity(ratingSummary);
    }
}
