package sultan.org.userservice.ratingsSummaryTest.serviceTest;


import jakarta.persistence.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sultan.org.userservice.common.util.JwtUtil;
import sultan.org.userservice.ratingsummary.model.dto.response.RatingSummaryResponseDto;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;
import sultan.org.userservice.ratingsummary.service.RatingSummaryService;
import sultan.org.userservice.ratingsummary.service.impl.RatingSummaryServiceImpl;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.service.UserService;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RatingSummaryServiceImplTest {
    @Mock
    UserService userService;
    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    RatingSummaryServiceImpl ratingSummaryService;

    String token;
    UUID keycloakId;
    User user;
    RatingSummary ratingSummary;
    @BeforeEach
    void setUp() {
        token = "tokenSecret";

        keycloakId = UUID.randomUUID();

        ratingSummary = RatingSummary.builder()
                .averageRating(1.3)
                .totalReviews(10)
                .build();

        user = User.builder()
                .email("test@mail.com")
                .username("testuser")
                .keycloakId(keycloakId)
                .ratingSummary(ratingSummary)
                .build();


    }

    @Test
    void successGetRatingSummary(){
        when(jwtUtil.extractSubject(token)).thenReturn(keycloakId);
        when(userService.findUserByKeycloakId(keycloakId)).thenReturn(user);
        RatingSummaryResponseDto ratingSummaryResponseDto = ratingSummaryService.getMyRatingSummary(token);
        assertEquals(ratingSummaryResponseDto.getAverageRating(),ratingSummary.getAverageRating());
        assertEquals(ratingSummaryResponseDto.getTotalReviews(),ratingSummary.getTotalReviews());
        verify(user).getRatingSummary();
    }
}
//public class RatingSummaryServiceImpl implements RatingSummaryService {
//    private final UserService userService;
//    @Override
//    public RatingSummaryResponseDto getMyRatingSummary(String token) {
//        UUID keuCloakId = JwtUtil.extractSubject(token);
//        User user = userService.findUserByKeycloakId(keuCloakId);
//        RatingSummary ratingSummary = user.getRatingSummary();
//        return getRatingSummaryDtoBuild(ratingSummary);
//    }
//}