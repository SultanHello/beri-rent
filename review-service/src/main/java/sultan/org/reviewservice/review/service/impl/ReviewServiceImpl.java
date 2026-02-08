package sultan.org.reviewservice.review.service.impl;

import sultan.org.reviewservice.review.service.ReviewService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingClient bookingClient;

    public Review create(CreateReviewRequest request, UUID authorId) {

        BookingInternalResponse booking =
                bookingClient.getBooking(request.getBookingId());

        if (!"COMPLETED".equals(booking.getBookingStatus())) {
            throw new IllegalStateException("Booking not completed");
        }

        if (!booking.getRenterId().equals(authorId)) {
            throw new AccessDeniedException("Only renter can leave review");
        }

        if (reviewRepository.existsByBookingIdAndAuthorId(
                request.getBookingId(), authorId)) {
            throw new IllegalStateException("Review already exists");
        }

        Review review = Review.builder()
                .bookingId(request.getBookingId())
                .itemId(request.getItemId())
                .authorId(authorId)
                .targetUserId(request.getTargetUserId())
                .rating(request.getRating())
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .build();

        return reviewRepository.save(review);
    }

    public void respond(Long reviewId, UUID ownerId, String response) {
        Review review = get(reviewId);
        review.setOwnerResponse(response);
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public void markHelpful(Long reviewId) {
        Review review = get(reviewId);
        review.setHelpful(true);
        reviewRepository.save(review);
    }

    public Review get(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }
}