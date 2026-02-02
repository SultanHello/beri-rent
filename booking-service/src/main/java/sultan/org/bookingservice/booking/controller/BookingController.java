package sultan.org.bookingservice.booking.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sultan.org.bookingservice.booking.model.dto.AvailabilityRequest;
import sultan.org.bookingservice.booking.model.dto.CreateBookingRequest;
import sultan.org.bookingservice.booking.model.dto.UpdateBookingRequest;
import sultan.org.bookingservice.booking.model.entity.Booking;
import sultan.org.bookingservice.booking.service.BookingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /* ================= CREATE / UPDATE / DELETE ================= */

    @PostMapping
    public Booking createBooking(@RequestBody CreateBookingRequest request,
                                 @AuthenticationPrincipal Jwt jwt) {
        return bookingService.createBooking(
                request,
                UUID.fromString(jwt.getSubject())
        );
    }

    @PutMapping("/{bookingId}")
    public Booking updateBooking(@PathVariable Long bookingId,
                                 @RequestBody UpdateBookingRequest request,
                                 @AuthenticationPrincipal Jwt jwt) {
        return bookingService.updateBooking(
                bookingId,
                request,
                UUID.fromString(jwt.getSubject())
        );
    }

    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId,
                              @AuthenticationPrincipal Jwt jwt) {
        bookingService.cancelBooking(
                bookingId,
                UUID.fromString(jwt.getSubject())
        );
    }

    /* ================= AS RENTER ================= */

    @GetMapping("/as-renter")
    public List<Booking> getAsRenter(@AuthenticationPrincipal Jwt jwt) {
        return bookingService.getBookingsAsRenter(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/as-renter/upcoming")
    public List<Booking> getUpcoming(@AuthenticationPrincipal Jwt jwt) {
        return bookingService.getUpcomingBookings(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/as-renter/past")
    public List<Booking> getPast(@AuthenticationPrincipal Jwt jwt) {
        return bookingService.getPastBookings(
                UUID.fromString(jwt.getSubject())
        );
    }

    /* ================= AS OWNER ================= */

    @GetMapping("/as-owner")
    public List<Booking> getAsOwner(@AuthenticationPrincipal Jwt jwt) {
        return bookingService.getBookingsAsOwner(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/as-owner/pending")
    public List<Booking> getPending(@AuthenticationPrincipal Jwt jwt) {
        return bookingService.getPendingBookings(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/as-owner/active")
    public List<Booking> getActive(@AuthenticationPrincipal Jwt jwt) {
        return bookingService.getActiveBookings(
                UUID.fromString(jwt.getSubject())
        );
    }

    /* ================= ACTIONS ================= */

    @PostMapping("/{bookingId}/confirm")
    public void confirm(@PathVariable Long bookingId,
                        @AuthenticationPrincipal Jwt jwt) {
        bookingService.confirmBooking(
                bookingId,
                UUID.fromString(jwt.getSubject())
        );
    }

    @PostMapping("/{bookingId}/complete")
    public void complete(@PathVariable Long bookingId,
                         @AuthenticationPrincipal Jwt jwt) {
        bookingService.completeBooking(
                bookingId,
                UUID.fromString(jwt.getSubject())
        );
    }

    /* ================= AVAILABILITY ================= */

    @PostMapping("/check-availability")
    public boolean checkAvailability(@RequestBody AvailabilityRequest request) {
        return bookingService.checkAvailability(request);
    }
}