package sultan.org.bookingservice.booking.model.dto;

import lombok.Getter;
import lombok.Setter;
import sultan.org.bookingservice.booking.enums.Status;

@Getter
@Setter
public class UpdateStatusRequest {
    private Status status;
}