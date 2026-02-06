package sultan.org.paymentservice.client;

import jdk.jshell.Snippet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sultan.org.paymentservice.enums.PaymentStatus;

@FeignClient(name = "booking-service")
@Service
public interface BookingClient {

    @PostMapping("/internal/bookings/{bookingId}/status")
    void updateStatus(@PathVariable Long bookingId,
                      @RequestBody PaymentStatus status);
}