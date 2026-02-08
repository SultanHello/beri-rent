package sultan.org.reviewservice.review.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sultan.org.reviewservice.review.model.dto.BookingInternalResponse;

@FeignClient(name = "booking-service")
@Service
public interface BookingClient {

    @GetMapping("/internal/bookings/{bookingId}")
    BookingInternalResponse getBooking(@PathVariable Long bookingId);
}