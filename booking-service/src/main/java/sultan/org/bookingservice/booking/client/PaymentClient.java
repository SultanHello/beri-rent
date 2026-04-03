package sultan.org.bookingservice.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @GetMapping("/payments/booking/{bookingId}/is-paid")
    boolean isBookingPaid(@PathVariable Long bookingId);
}