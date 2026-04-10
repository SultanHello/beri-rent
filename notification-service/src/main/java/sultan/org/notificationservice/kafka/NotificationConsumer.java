package sultan.org.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sultan.org.notificationservice.kafka.event.BookingEvent;
import sultan.org.notificationservice.kafka.event.ItemEvent;
import sultan.org.notificationservice.kafka.event.PaymentEvent;
import sultan.org.notificationservice.model.entity.Notification;
import sultan.org.notificationservice.service.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;


    @KafkaListener(topics = "booking-created", groupId = "notification-service")
    public void onBookingCreated(BookingEvent event) {
        notificationService.send(Notification.builder()
                .userId(event.getOwnerId())
                .title("New rental request")
                .message("Someone wants to rent your item")
                .type("BOOKING_CREATED")
                .build());
    }

    @KafkaListener(topics = "booking-confirmed", groupId = "notification-service")
    public void onBookingConfirmed(BookingEvent event) {
        notificationService.send(Notification.builder()
                .userId(event.getRenterId())
                .title("Booking confirmed")
                .message("The owner has confirmed your booking")
                .type("BOOKING_CONFIRMED")
                .build());
    }

    @KafkaListener(topics = "booking-cancelled", groupId = "notification-service")
    public void onBookingCancelled(BookingEvent event) {
        notificationService.send(Notification.builder()
                .userId(event.getOwnerId())
                .title("Booking cancelled")
                .message("The booking has been cancelled")
                .type("BOOKING_CANCELLED")
                .build());

        notificationService.send(Notification.builder()
                .userId(event.getRenterId())
                .title("Booking cancelled")
                .message("The booking has been cancelled")
                .type("BOOKING_CANCELLED")
                .build());
    }

    @KafkaListener(topics = "booking-completed", groupId = "notification-service")
    public void onBookingCompleted(BookingEvent event) {
        notificationService.send(Notification.builder()
                .userId(event.getRenterId())
                .title("Rental completed")
                .message("Please leave a review for your rental")
                .type("BOOKING_COMPLETED")
                .build());
    }

    @KafkaListener(topics = "payment-confirmed", groupId = "notification-service")
    public void onPaymentConfirmed(PaymentEvent event) {
        notificationService.send(Notification.builder()
                .userId(event.getRenterId())
                .title("Payment successful")
                .message("Your payment has been confirmed")
                .type("PAYMENT_CONFIRMED")
                .build());

        notificationService.send(Notification.builder()
                .userId(event.getOwnerId())
                .title("Payment received")
                .message("You have received a payment for your rental")
                .type("PAYMENT_RECEIVED")
                .build());
    }

    @KafkaListener(topics = "item-created", groupId = "notification-service")
    public void onItemCreated(ItemEvent event) {
        notificationService.send(Notification.builder()
                .userId(event.getOwnerId())
                .title("Item successfully created")
                .message("Your item \"" + event.getTitle() + "\" has been created")
                .type("ITEM_CREATED")
                .build());
    }
}