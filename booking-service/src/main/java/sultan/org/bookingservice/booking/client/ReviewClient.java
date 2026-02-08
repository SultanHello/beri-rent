package sultan.org.bookingservice.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "review-service")
@Service
public interface ReviewClient {

    @GetMapping("/internal/reviews/exists")
    boolean existsReview(@RequestParam Long bookingId,
                         @RequestParam UUID authorId);
}