package sultan.org.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import sultan.org.paymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    private UUID payerId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String stripePaymentIntentId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}