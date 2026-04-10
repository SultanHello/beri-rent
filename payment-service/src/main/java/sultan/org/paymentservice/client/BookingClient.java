package sultan.org.paymentservice.client;

import jdk.jshell.Snippet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sultan.org.paymentservice.enums.PaymentStatus;

import java.util.UUID;

@FeignClient(name = "booking-service")
@Service
public interface BookingClient {
    @GetMapping("/internal/bookings/{bookingId}/ownerId")
    UUID getOwnerId(@PathVariable Long bookingId);

    @PostMapping("/internal/bookings/{bookingId}/status")
    void updateStatus(@PathVariable Long bookingId,
                      @RequestBody PaymentStatus status);
}