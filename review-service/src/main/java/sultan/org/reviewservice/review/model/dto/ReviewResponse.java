package sultan.org.reviewservice.review.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class ReviewResponse {
    private Long id;
    private int rating;
    private String text;
    private UUID authorId;
    private UUID targetUserId;
    private String ownerResponse;
    private boolean helpful;
    private LocalDateTime createdAt;
}