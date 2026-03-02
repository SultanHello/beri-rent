package sultan.org.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sultan.org.notificationservice.model.entity.NotificationPreferences;

public interface NotificationPreferencesRepository
        extends JpaRepository<NotificationPreferences, Long> {
}