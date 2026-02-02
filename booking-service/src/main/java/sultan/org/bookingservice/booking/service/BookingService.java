package sultan.org.bookingservice.booking.service;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import sultan.org.bookingservice.booking.enums.Status;
import sultan.org.bookingservice.booking.model.dto.AvailabilityRequest;
import sultan.org.bookingservice.booking.model.dto.CreateBookingRequest;
import sultan.org.bookingservice.booking.model.dto.UpdateBookingRequest;
import sultan.org.bookingservice.booking.model.entity.Booking;

import java.util.List;
import java.util.UUID;


public interface BookingService {

    Booking createBooking(CreateBookingRequest request, UUID renterId);

    Booking updateBooking(Long bookingId, UpdateBookingRequest request, UUID renterId);

    void cancelBooking(Long bookingId, UUID userId);

    /* RENTER */
    List<Booking> getBookingsAsRenter(UUID renterId);
    List<Booking> getUpcomingBookings(UUID renterId);
    List<Booking> getPastBookings(UUID renterId);

    /* OWNER */
    List<Booking> getBookingsAsOwner(UUID ownerId);
    List<Booking> getPendingBookings(UUID ownerId);
    List<Booking> getActiveBookings(UUID ownerId);

    /* ACTIONS */
    void confirmBooking(Long bookingId, UUID ownerId);
    void completeBooking(Long bookingId, UUID ownerId);

    /* AVAILABILITY */
    boolean checkAvailability(AvailabilityRequest request);

    /* INTERNAL */
    Booking getInternalBooking(Long bookingId);
    void updateStatusFromPayment(Long bookingId, Status status);
}
