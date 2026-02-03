package sultan.org.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sultan.org.paymentservice.model.dto.CreatePaymentRequest;
import sultan.org.paymentservice.model.dto.PaymentIntentResponse;
import sultan.org.paymentservice.model.entity.Payment;
import sultan.org.paymentservice.service.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public List<Payment> myPayments(@AuthenticationPrincipal Jwt jwt) {
        return paymentService.getMyPayments(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/{paymentId}")
    public Payment get(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @GetMapping("/booking/{bookingId}")
    public List<Payment> byBooking(@PathVariable Long bookingId) {
        return paymentService.getByBooking(bookingId);
    }

    @PostMapping
    public Payment create(@RequestBody CreatePaymentRequest request,
                          @AuthenticationPrincipal Jwt jwt) {
        return paymentService.createPayment(
                request,
                UUID.fromString(jwt.getSubject())
        );
    }

    @PostMapping("/create-intent")
    public PaymentIntentResponse createIntent(@RequestParam Long paymentId) {
        return paymentService.createIntent(paymentId);
    }

    @PostMapping("/confirm")
    public void confirm(@RequestParam Long paymentId) {
        paymentService.confirmPayment(paymentId);
    }

    @PostMapping("/refund/{paymentId}")
    public void refund(@PathVariable Long paymentId) {
        paymentService.refund(paymentId);
    }
}