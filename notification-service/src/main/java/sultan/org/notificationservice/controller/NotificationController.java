package sultan.org.notificationservice.controller;

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


    @GetMapping
    public List<Notification> getAll(@RequestParam Long userId) {
        return notificationService.getAll(userId);
    }


    @GetMapping("/unread")
    public List<Notification> getUnread(@RequestParam Long userId) {
        return notificationService.getUnread(userId);
    }


    @PostMapping("/{id}/mark-read")
    public void markRead(@PathVariable Long id) {
        notificationService.markRead(id);
    }


    @PostMapping("/mark-all-read")
    public void markAllRead(@RequestParam Long userId) {
        notificationService.markAllRead(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }
}