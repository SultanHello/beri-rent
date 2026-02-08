package sultan.org.reviewservice.review.service;

import sultan.org.reviewservice.review.model.dto.CreateReviewRequest;
import sultan.org.reviewservice.review.model.entity.Review;

import java.util.UUID;

public interface ReviewService {
    public Review create(CreateReviewRequest request, UUID authorId);
    public void respond(Long reviewId, UUID ownerId, String response);
    public void markHelpful(Long reviewId);
    public Review get(Long id);
}
