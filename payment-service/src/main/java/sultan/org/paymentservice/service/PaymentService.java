package sultan.org.paymentservice.service;

import sultan.org.paymentservice.enums.PaymentStatus;
import sultan.org.paymentservice.model.dto.CreatePaymentRequest;
import sultan.org.paymentservice.model.dto.PaymentIntentResponse;
import sultan.org.paymentservice.model.dto.ProcessPaymentRequest;
import sultan.org.paymentservice.model.entity.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    Payment createPayment(CreatePaymentRequest request, UUID payerId);

    List<Payment> getMyPayments(UUID payerId);

    List<Payment> getByBooking(Long bookingId);

    Payment getPayment(Long paymentId);

    PaymentIntentResponse createIntent(Long paymentId);

    void confirmPayment(Long paymentId);

    void refund(Long paymentId);

    /* INTERNAL */
    Payment processPayment(ProcessPaymentRequest request);

    PaymentStatus getStatus(Long paymentId);
}