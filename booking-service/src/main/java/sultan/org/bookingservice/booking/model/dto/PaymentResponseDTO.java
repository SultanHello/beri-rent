package sultan.org.bookingservice.booking.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentResponseDTO {
    private Long id;
    private Long bookingId;
    private UUID payerId;
    private BigDecimal amount;
    //    private PaymentStatus status;
    private String stripePaymentIntentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}