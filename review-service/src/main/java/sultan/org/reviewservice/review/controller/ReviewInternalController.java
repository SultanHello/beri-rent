package sultan.org.reviewservice.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sultan.org.reviewservice.review.repository.ReviewRepository;

import java.util.UUID;

@RestController
@RequestMapping("/internal/reviews")
@RequiredArgsConstructor
public class ReviewInternalController {

    private final ReviewRepository reviewRepository;

    @GetMapping("/exists")
    public boolean exists(@RequestParam Long bookingId,
                          @RequestParam UUID authorId) {
        return reviewRepository
                .existsByBookingIdAndAuthorId(bookingId, authorId);
    }
}