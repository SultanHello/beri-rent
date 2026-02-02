package sultan.org.bookingservice.service.impl;

import lombok.RequiredArgsConstructor;
import sultan.org.bookingservice.booking.exceptions.BookingNotFoundException;
import sultan.org.bookingservice.booking.model.entity.Booking;
import sultan.org.bookingservice.booking.repository.BookingRepository;
import sultan.org.bookingservice.service.BookingService;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> getMyBookings(UUID renterUUID) {
        return bookingRepository.findByRenterId(renterUUID);
    }

    @Override
    public Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }
}
