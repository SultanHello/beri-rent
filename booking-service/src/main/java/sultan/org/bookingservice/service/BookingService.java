package sultan.org.bookingservice.service;

import org.springframework.stereotype.Service;
import sultan.org.bookingservice.booking.model.entity.Booking;

import java.util.List;
import java.util.UUID;


@Service
public interface BookingService {
    List<Booking> getMyBookings(UUID renterUUID);
}
