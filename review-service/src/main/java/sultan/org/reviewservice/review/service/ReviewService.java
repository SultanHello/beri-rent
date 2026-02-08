package sultan.org.reviewservice.review.service;

public interface ReviewService {
    Review create(CreateReviewRequest request, UUID authorId);
    respond(Long reviewId, UUID ownerId, String response);
    void markHelpful(Long reviewId)
    public Review get(Long id);
}
