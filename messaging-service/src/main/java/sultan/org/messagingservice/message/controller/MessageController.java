package sultan.org.messagingservice.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sultan.org.messagingservice.message.model.entity.Message;
import sultan.org.messagingservice.message.service.MessageService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController{

    private final MessageService messageService;

    @GetMapping("/api/conversations/{id}/messages")
    public List<Message> messages(@PathVariable Long id) {
        return messageService.getMessages(id);
    }

    @PostMapping("/api/conversations/{id}/messages")
    public Message send(@PathVariable Long id,
                        @RequestBody String content,
                        @AuthenticationPrincipal Jwt jwt) {

        return messageService.send(
                id,
                UUID.fromString(jwt.getSubject()),
                content
        );
    }

    @DeleteMapping("/api/messages/{messageId}")
    public void delete(@PathVariable Long messageId,
                       @AuthenticationPrincipal Jwt jwt) throws AccessDeniedException {
        messageService.delete(
                messageId,
                UUID.fromString(jwt.getSubject())
        );
    }
}