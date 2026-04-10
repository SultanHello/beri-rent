package sultan.org.paymentservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sultan.org.paymentservice.enums.PaymentStatus;

// в payment-service
@Getter
@AllArgsConstructor
public class UpdateBookingStatusRequest {
    private PaymentStatus status;

}