package sultan.org.reviewservice.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sultan.org.reviewservice.review.model.entity.Review;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @GetMapping("/user/{userId}")
    public List<Review> byUser(@PathVariable UUID userId) {
        return reviewRepository.findByTargetUserId(userId);
    }

    @GetMapping("/item/{itemId}")
    public List<Review> byItem(@PathVariable Long itemId) {
        return reviewRepository.findByItemId(itemId);
    }

    @GetMapping("/{id}")
    public Review get(@PathVariable Long id) {
        return reviewService.get(id);
    }

    @PostMapping
    public Review create(@RequestBody CreateReviewRequest request,
                         @AuthenticationPrincipal Jwt jwt) {
        return reviewService.create(
                request,
                UUID.fromString(jwt.getSubject())
        );
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id,
                       @RequestBody UpdateReviewRequest request,
                       @AuthenticationPrincipal Jwt jwt) {
        // можно добавить проверку автора
    }

    @PostMapping("/{id}/response")
    public void respond(@PathVariable Long id,
                        @RequestBody String response,
                        @AuthenticationPrincipal Jwt jwt) {
        reviewService.respond(id, UUID.fromString(jwt.getSubject()), response);
    }

    @PostMapping("/{id}/helpful")
    public void helpful(@PathVariable Long id) {
        reviewService.markHelpful(id);
    }

    @GetMapping("/my/given")
    public List<Review> myGiven(@AuthenticationPrincipal Jwt jwt) {
        return reviewRepository.findByAuthorId(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/my/received")
    public List<Review> myReceived(@AuthenticationPrincipal Jwt jwt) {
        return reviewRepository.findByTargetUserId(
                UUID.fromString(jwt.getSubject())
        );
    }
}