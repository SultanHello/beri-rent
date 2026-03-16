package sultan.org.userservice.ratingsummary.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sultan.org.userservice.ratingsummary.service.RatingSummaryService;

@Component
@RequiredArgsConstructor
public class RatingEventListener {

    private final RatingSummaryService ratingSummaryService;

    @KafkaListener(topics = "review-created", groupId = "user-service")
    public void onReviewCreated(RatingEvent event) {
        ratingSummaryService.updateRating(event.getTargetUserId(), event.getRating());
    }
}