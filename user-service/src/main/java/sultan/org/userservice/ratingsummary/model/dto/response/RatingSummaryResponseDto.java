package sultan.org.userservice.ratingsummary.model.dto.response;


import lombok.*;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;
import sultan.org.userservice.user.model.entity.User;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingSummaryResponseDto {
    private Long id;
    private User user;

    private double averageRating;

    private int totalReviews;

    private int fiveStarCount;

    private int fourStarCount;

    private int threeStarCount;

    private int twoStarCount;

    private int oneStarCount;

    public static RatingSummaryResponseDto fromEntity(RatingSummary ratingSummary){
        return RatingSummaryResponseDto.builder()
                .id(ratingSummary.getId())
                .averageRating(ratingSummary.getAverageRating())
                .oneStarCount(ratingSummary.getOneStarCount())
                .twoStarCount(ratingSummary.getTwoStarCount())
                .threeStarCount(ratingSummary.getThreeStarCount())
                .fourStarCount(ratingSummary.getFourStarCount())
                .fiveStarCount(ratingSummary.getFiveStarCount())
                .totalReviews(ratingSummary.getTotalReviews())
                .build();
    }
}
