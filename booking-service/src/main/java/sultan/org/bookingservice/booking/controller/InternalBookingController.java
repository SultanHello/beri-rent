package sultan.org.bookingservice.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sultan.org.bookingservice.booking.model.dto.UpdateStatusRequest;
import sultan.org.bookingservice.booking.model.entity.Booking;
import sultan.org.bookingservice.booking.service.BookingService;

@RestController
@RequestMapping("/internal/bookings")
@RequiredArgsConstructor
public class InternalBookingController {

    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public Booking getInternal(@PathVariable Long bookingId) {
        return bookingService.getInternalBooking(bookingId);
    }

    @PostMapping("/{bookingId}/status")
    public void updateStatus(@PathVariable Long bookingId,
                             @RequestBody UpdateStatusRequest request) {
        bookingService.updateStatusFromPayment(
                bookingId,
                request.getStatus()
        );
    }
}