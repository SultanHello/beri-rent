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
}