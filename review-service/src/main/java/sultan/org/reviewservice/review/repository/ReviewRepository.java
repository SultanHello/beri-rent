package sultan.org.reviewservice.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.reviewservice.review.model.dto.BookingInternalResponse;
import sultan.org.reviewservice.review.model.entity.Review;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTargetUserId(UUID userId);

    List<Review> findByItemId(Long itemId);

    List<Review> findByAuthorId(UUID authorId);

    boolean existsByBookingIdAndAuthorId(Long bookingId, UUID authorId);

}