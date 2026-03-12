package sultan.org.notificationservice.controller.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sultan.org.notificationservice.model.entity.Notification;
import sultan.org.notificationservice.service.NotificationService;

@RestController
@RequestMapping("/internal/notifications")
@RequiredArgsConstructor
public class InternalNotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public Notification send(@RequestBody Notification notification) {
        return notificationService.send(notification);
    }
}