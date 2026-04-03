package sultan.org.messagingservice.conversation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sultan.org.messagingservice.conversation.entity.dto.ConversationResponseDto;
import sultan.org.messagingservice.conversation.entity.dto.CreateConversationRequestDto;
import sultan.org.messagingservice.conversation.model.entity.Conversation;
import sultan.org.messagingservice.conversation.service.ConversationService;

@RestController
@RequestMapping("/internal/conversations")
@RequiredArgsConstructor
public class InternalConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ResponseEntity<ConversationResponseDto> createConversation(
            @RequestBody CreateConversationRequestDto request) {
        
        Conversation conversation = conversationService.createInternal(
                request.getBookingId(),
                request.getRenterId(),
                request.getOwnerId()

        );

        // Маппим сущность в DTO перед отправкой
        return ResponseEntity.ok(ConversationResponseDto.fromEntity(conversation));
    }
}