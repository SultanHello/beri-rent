package sultan.org.itemservice.item.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProcessPaymentRequest {
    private Long bookingId;
    private BigDecimal amount;
}