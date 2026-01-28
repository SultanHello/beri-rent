package sultan.org.bookingservice.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.bookingservice.booking.model.entity.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
}
