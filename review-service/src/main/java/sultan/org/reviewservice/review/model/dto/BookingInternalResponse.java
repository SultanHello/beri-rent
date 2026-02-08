package sultan.org.reviewservice.review.model.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BookingInternalResponse {
    private Long id;
    private UUID renterId;
    private UUID ownerId;
    private Long itemId;
    private String bookingStatus;
}