package sultan.org.bookingservice.booking.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingEvent {
    private Long bookingId;
    private UUID ownerId;
    private UUID renterId;
    private String status; // CREATED, CONFIRMED, CANCELLED, COMPLETED
}