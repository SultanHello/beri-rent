package sultan.org.bookingservice.booking.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
public class AvailabilityRequest {

    private Long itemId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
