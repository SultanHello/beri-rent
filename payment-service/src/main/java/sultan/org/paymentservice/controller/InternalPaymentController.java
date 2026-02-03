package sultan.org.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sultan.org.paymentservice.enums.PaymentStatus;
import sultan.org.paymentservice.model.dto.ProcessPaymentRequest;
import sultan.org.paymentservice.model.entity.Payment;
import sultan.org.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/internal/payments")
@RequiredArgsConstructor
public class InternalPaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public Payment process(@RequestBody ProcessPaymentRequest request) {
        return paymentService.processPayment(request);
    }

    @GetMapping("/status/{paymentId}")
    public PaymentStatus status(@PathVariable Long paymentId) {
        return paymentService.getStatus(paymentId);
    }
}