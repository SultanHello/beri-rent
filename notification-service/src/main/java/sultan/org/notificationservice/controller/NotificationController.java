package sultan.org.notificationservice.controller;

import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sultan.org.notificationservice.model.entity.Notification;
import sultan.org.notificationservice.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // GET /api/notifications
    @GetMapping
    public List<Notification> getAll(@RequestParam Long userId) {
        return notificationService.getAll(userId);
    }

    // GET /api/notifications/unread
    @GetMapping("/unread")
    public List<Notification> getUnread(@RequestParam Long userId) {
        return notificationService.getUnread(userId);
    }

    // POST /api/notifications/{id}/mark-read
    @PostMapping("/{id}/mark-read")
    public void markRead(@PathVariable Long id) {
        notificationService.markRead(id);
    }

    // POST /api/notifications/mark-all-read
    @PostMapping("/mark-all-read")
    public void markAllRead(@RequestParam Long userId) {
        notificationService.markAllRead(userId);
    }

    // DELETE /api/notifications/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }
}