package sultan.org.bookingservice.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sultan.org.bookingservice.booking.enums.Status;
import sultan.org.bookingservice.booking.model.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRenterId(UUID renterId);

    List<Booking> findByOwnerId(UUID ownerId);

    List<Booking> findByOwnerIdAndBookingStatus(UUID ownerId, Status status);

    List<Booking> findByRenterIdAndEndDateBefore(
            UUID renterId,
            LocalDateTime dateTime
    );


    List<Booking> findByRenterIdAndStartDateAfter(
            UUID renterId,
            LocalDateTime dateTime
    );
    @Query("""
        select b from Booking b
        where b.itemId = :itemId
        and b.startDate < :endDate
        and b.endDate > :startDate
    """)
    List<Booking> findConflictingBookings(
            Long itemId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
