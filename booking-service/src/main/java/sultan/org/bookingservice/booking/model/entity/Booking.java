package sultan.org.bookingservice.booking.model.entity;


import jakarta.persistence.*;
import sultan.org.bookingservice.booking.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long itemId;
    private UUID ownerId;
    private UUID renterId;
    private LocalDateTime startDate;
    private Status bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;




}
