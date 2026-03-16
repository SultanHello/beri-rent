package sultan.org.userservice.ratingsummary.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sultan.org.userservice.common.util.JwtUtil;
import sultan.org.userservice.ratingsummary.model.dto.response.RatingSummaryResponseDto;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;
import sultan.org.userservice.ratingsummary.service.RatingSummaryService;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/users/rating-summaries")
@RequiredArgsConstructor
public class RatingSummaryController {

    private final RatingSummaryService ratingSummaryService;
    @GetMapping("/my")
    public RatingSummaryResponseDto getMyRatingSummary(@RequestHeader("Authorization") String token){
        return ratingSummaryService.getMyRatingSummary(token);

    }
    @GetMapping("/user/{keycloakId}")
    public RatingSummaryResponseDto getUserRatingSummary(@PathVariable UUID keycloakId) {
        return ratingSummaryService.getUserRatingSummaryById(keycloakId);
    }


}
