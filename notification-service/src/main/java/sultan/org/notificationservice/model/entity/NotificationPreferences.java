package sultan.org.notificationservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_preferences")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferences {

    @Id
    private Long userId;
    private boolean emailEnabled;
    private boolean pushEnabled;
    private boolean inAppEnabled;
}