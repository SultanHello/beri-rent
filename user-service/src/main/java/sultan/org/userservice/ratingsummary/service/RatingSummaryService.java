package sultan.org.userservice.ratingsummary.service;

import org.springframework.stereotype.Service;
import sultan.org.userservice.ratingsummary.model.dto.response.RatingSummaryResponseDto;

@Service
public interface RatingSummaryService {


    RatingSummaryResponseDto getMyRatingSummary(String token);

    RatingSummaryResponseDto getUserRatingSummaryById(Long id);
}
