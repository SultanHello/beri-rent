package sultan.org.messagingservice.conversation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sultan.org.messagingservice.conversation.model.entity.Conversation;
import sultan.org.messagingservice.conversation.service.ConversationService;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping
    public List<Conversation> getAll(@AuthenticationPrincipal Jwt jwt) {
        return conversationService.getMyConversations(
                UUID.fromString(jwt.getSubject())
        );
    }

    @GetMapping("/{id}")
    public Conversation get(@PathVariable Long id) {
        return conversationService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void archive(@PathVariable Long id,
                        @AuthenticationPrincipal Jwt jwt) {
        conversationService.archive(id, UUID.fromString(jwt.getSubject()));
    }

    @GetMapping("/unread")
    public long unread(@AuthenticationPrincipal Jwt jwt) {
        return conversationService.getUnreadCount(
                UUID.fromString(jwt.getSubject())
        );
    }

    @PostMapping("/{id}/mark-read")
    public void markRead(@PathVariable Long id,
                         @AuthenticationPrincipal Jwt jwt) {
        conversationService.markAsRead(
                id,
                UUID.fromString(jwt.getSubject())
        );
    }
}
