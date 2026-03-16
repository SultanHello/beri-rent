package sultan.org.userservice.ratingsummary.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.userservice.common.util.JwtUtil;
import sultan.org.userservice.ratingsummary.model.dto.response.RatingSummaryResponseDto;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;
import sultan.org.userservice.ratingsummary.repository.RatingSummaryRepository;
import sultan.org.userservice.ratingsummary.service.RatingSummaryService;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingSummaryServiceImpl implements RatingSummaryService {
    private final UserService userService;
    private final RatingSummaryRepository ratingSummaryRepository;
    private final JwtUtil jwtUtil;
    @Override
    public RatingSummaryResponseDto getMyRatingSummary(String token) {
        UUID keuCloakId = jwtUtil.extractSubject(token);
        User user = userService.findUserByKeycloakId(keuCloakId);
        RatingSummary ratingSummary = user.getRatingSummary();
        return RatingSummaryResponseDto.fromEntity(ratingSummary);
    }

    @Override
    public RatingSummaryResponseDto getUserRatingSummaryById(UUID keycloakId) {
        User user = userService.findUserByKeycloakId(keycloakId);
        RatingSummary ratingSummary = user.getRatingSummary();
        return RatingSummaryResponseDto.fromEntity(ratingSummary);
    }

    public void updateRating(UUID keycloakId, int newRating) {
        User user = userService.findUserByKeycloakId(keycloakId);
        RatingSummary summary = ratingSummaryRepository.findByUser(user)
                .orElse(RatingSummary.builder().user(user).build());

        int oldTotal = summary.getTotalReviews();
        double newAverage = (summary.getAverageRating() * oldTotal + newRating) / (oldTotal + 1);

        summary.setTotalReviews(oldTotal + 1);
        summary.setAverageRating(newAverage);

        ratingSummaryRepository.save(summary);
    }
}
