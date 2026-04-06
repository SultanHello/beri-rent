package sultan.org.notificationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sultan.org.notificationservice.model.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(UUID userId);

    List<Notification> findByUserIdAndReadFalse(UUID userId);

}