package sultan.org.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sultan.org.notificationservice.model.entity.Notification;
import sultan.org.notificationservice.service.NotificationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getAll(@AuthenticationPrincipal Jwt jwt) {
        return notificationService.getAll(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/unread")
    public List<Notification> getUnread(@AuthenticationPrincipal Jwt jwt) {
        return notificationService.getUnread(
                UUID.fromString(jwt.getSubject())
        );
    }

    @PostMapping("/{id}/mark-read")
    public void markRead(@PathVariable Long id) {
        notificationService.markRead(id);
    }

    @PostMapping("/mark-all-read")
    public void markAllRead(@AuthenticationPrincipal Jwt jwt) {
        notificationService.markAllRead(
                UUID.fromString(jwt.getSubject())
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }
}