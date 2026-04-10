package sultan.org.paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sultan.org.paymentservice.client.BookingClient;
import sultan.org.paymentservice.enums.PaymentStatus;
import sultan.org.paymentservice.kafka.event.PaymentEvent;
import sultan.org.paymentservice.model.dto.CreatePaymentRequest;
import sultan.org.paymentservice.model.dto.PaymentIntentResponse;
import sultan.org.paymentservice.model.dto.ProcessPaymentRequest;
import sultan.org.paymentservice.model.dto.UpdateBookingStatusRequest;
import sultan.org.paymentservice.model.entity.Payment;
import sultan.org.paymentservice.repository.PaymentRepository;
import sultan.org.paymentservice.service.PaymentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingClient bookingClient; // Feign / RestTemplate
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Override
    public Payment createPayment(CreatePaymentRequest request, UUID payerId) {
        Payment payment = Payment.builder()
                .bookingId(request.getBookingId())
                .renterId(payerId)
                .ownerId(bookingClient.getOwnerId(request.getBookingId()))
                .amount(request.getAmount())
                .status(PaymentStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }


    @Override
    public List<Payment> getMyPayments(UUID payerId) {
        return paymentRepository.findByRenterId(payerId);
    }

    @Override
    public Payment getByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    @Override
    public Payment getPayment(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Override
    public PaymentIntentResponse createIntent(Long paymentId) {
        Payment payment = getPayment(paymentId);

        payment.setStatus(PaymentStatus.PENDING);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        // mock stripe secret
        return new PaymentIntentResponse("stripe_client_secret_mock");
    }

    @Override
    public void confirmPayment(Long paymentId) {
        Payment payment = getPayment(paymentId);

        payment.setStatus(PaymentStatus.PAID);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        // 🔥 уведомляем Booking
        bookingClient.updateStatus(payment.getBookingId(), new UpdateBookingStatusRequest(PaymentStatus.PAID));
        kafkaTemplate.send("payment-confirmed", new PaymentEvent(
                payment.getId(),
                payment.getBookingId(),
                payment.getRenterId(),
                payment.getOwnerId(),
                "PAID"// нужно добавить в Payment entity
        ));
    }

    @Override
    public void refund(Long paymentId) {
        Payment payment = getPayment(paymentId);

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    /* ============ INTERNAL ============ */

    @Override
    public Payment processPayment(ProcessPaymentRequest request) {
        Payment payment = Payment.builder()
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .status(PaymentStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public PaymentStatus getStatus(Long paymentId) {
        return getPayment(paymentId).getStatus();
    }
}