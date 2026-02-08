package sultan.org.messagingservice.webSocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sultan.org.messagingservice.message.model.dto.ChatMessageDto;
import sultan.org.messagingservice.message.service.MessageService;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/chat/{conversationId}")
    public ChatMessageDto send(
            @DestinationVariable Long conversationId,
            ChatMessageDto dto
    ) {
        messageService.saveMessage(
                conversationId,
                dto.getSenderId(),
                dto.getContent()
        );

        return dto;
    }
}