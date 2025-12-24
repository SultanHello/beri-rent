package sultan.org.userservice.ratingsummary.model.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import sultan.org.userservice.user.model.entity.User;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingSummaryRequestDto {
    private Long id;
    private User user;

    private double averageRating;

    private int totalReviews;

    private int fiveStarCount;

    private int fourStarCount;

    private int threeStarCount;

    private int twoStarCount;

    private int oneStarCount;
}
