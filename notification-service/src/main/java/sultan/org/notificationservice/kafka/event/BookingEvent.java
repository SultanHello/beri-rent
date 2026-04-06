package sultan.org.notificationservice.kafka.event;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingEvent {
    private Long bookingId;
    private UUID ownerId;
    private UUID renterId;
    private String status; // CREATED, CONFIRMED, CANCELLED, COMPLETED
}