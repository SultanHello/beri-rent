package sultan.org.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.paymentservice.model.entity.Payment;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByPayerId(UUID payerId);

    List<Payment> findByBookingId(Long bookingId);
}