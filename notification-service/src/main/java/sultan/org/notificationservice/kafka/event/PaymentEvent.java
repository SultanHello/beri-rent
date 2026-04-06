package sultan.org.notificationservice.kafka.event;

import lombok.*;

import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentEvent {
    private Long paymentId;
    private Long bookingId;
    private UUID renterId;
    private UUID ownerId;
    private String status; // CONFIRMED, REFUNDED
}