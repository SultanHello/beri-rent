package sultan.org.paymentservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatePaymentRequest {
    private Long bookingId;
    private BigDecimal amount;
}