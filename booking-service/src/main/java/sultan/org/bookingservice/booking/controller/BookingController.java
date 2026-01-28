package sultan.org.bookingservice.booking.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sultan.org.bookingservice.booking.model.entity.Booking;
import sultan.org.bookingservice.service.BookingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @GetMapping
    public List<Booking> getMyBookings(@AuthenticationPrincipal Jwt jwt){
        return bookingService.getMyBookings( UUID.fromString(jwt.getSubject()));
    }

}
