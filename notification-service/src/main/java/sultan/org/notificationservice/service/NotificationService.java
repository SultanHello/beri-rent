package sultan.org.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.notificationservice.model.entity.Notification;
import sultan.org.notificationservice.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getAll(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getUnread(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId);
    }

    public void markRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void markAllRead(Long userId) {
        List<Notification> notifications =
                notificationRepository.findByUserIdAndReadFalse(userId);

        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }

    public Notification send(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    @KafkaListener(topics = "booking-created", groupId = "notification-service")
    public void onBookingCreated(BookingEvent event) {
        // овнеру
        send(Notification.builder()
                .userId(event.getOwnerId())
                .title("Новый запрос на аренду")
                .type("BOOKING_CREATED")
                .build());
    }

    @KafkaListener(topics = "booking-confirmed", groupId = "notification-service")
    public void onBookingConfirmed(BookingEvent event) {
        // рентеру
        send(Notification.builder()
                .userId(event.getRenterId())
                .title("Овнер подтвердил букинг")
                .type("BOOKING_CONFIRMED")
                .build());
    }

    @KafkaListener(topics = "payment-confirmed", groupId = "notification-service")
    public void onPaymentConfirmed(PaymentEvent event) {
        // рентеру
        send(Notification.builder()
                .userId(event.getPayerId())
                .title("Оплата прошла успешно")
                .type("PAYMENT_CONFIRMED")
                .build());
    }
}