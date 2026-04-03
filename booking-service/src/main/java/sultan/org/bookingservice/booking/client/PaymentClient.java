package sultan.org.bookingservice.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sultan.org.bookingservice.booking.model.dto.PaymentResponseDTO;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @GetMapping("/payments/booking/{bookingId}/is-paid")
    boolean isBookingPaid(@PathVariable Long bookingId);

    @PostMapping("/payments/refund/{bookingId}")
    void refund(@PathVariable Long bookingId);

    @GetMapping("/api/payments/booking/{bookingId}")
    PaymentResponseDTO getByBooking(@PathVariable Long bookingId);
}
