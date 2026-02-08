package sultan.org.reviewservice.review.model.dto;

import lombok.Getter;

@Getter
public class UpdateReviewRequest {
    private int rating;
    private String text;
}