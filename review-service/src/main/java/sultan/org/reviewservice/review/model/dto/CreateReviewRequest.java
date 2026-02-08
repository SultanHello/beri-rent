package sultan.org.reviewservice.review.model.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateReviewRequest {
    private Long bookingId;
    private Long itemId;
    private UUID targetUserId;
    private int rating;
    private String text;
}