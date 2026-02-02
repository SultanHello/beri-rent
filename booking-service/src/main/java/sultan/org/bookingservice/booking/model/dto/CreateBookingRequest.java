package sultan.org.bookingservice.booking.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateBookingRequest {
    private Long itemId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}