package sultan.org.bookingservice.booking.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private UUID ownerId;
}