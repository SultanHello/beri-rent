package sultan.org.reviewservice.review.kafka;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingEvent {
    private UUID targetUserId;
    private int rating;
}