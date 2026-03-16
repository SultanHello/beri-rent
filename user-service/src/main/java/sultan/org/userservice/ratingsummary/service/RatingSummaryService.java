package sultan.org.userservice.ratingsummary.service;

import org.springframework.stereotype.Service;
import sultan.org.userservice.ratingsummary.model.dto.response.RatingSummaryResponseDto;

import java.util.UUID;

@Service
public interface RatingSummaryService {
    public void updateRating(UUID keuCloakId, int newRating);


    RatingSummaryResponseDto getMyRatingSummary(String token);

    RatingSummaryResponseDto getUserRatingSummaryById(UUID keuCloakId);
}
