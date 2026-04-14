package sultan.org.messagingservice.webSocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import sultan.org.messagingservice.message.model.dto.ChatMessageDto;
import sultan.org.messagingservice.message.model.entity.Message;
import sultan.org.messagingservice.message.service.MessageService;
import sultan.org.messagingservice.message.service.OnlineStatusService;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final OnlineStatusService onlineStatusService;

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/chat/{conversationId}")
    public Message send(
            @DestinationVariable Long conversationId,
            ChatMessageDto dto,
            Principal principal
    ) {
        UUID senderId = UUID.fromString(principal.getName());


        return messageService.send(
                conversationId,
                senderId,
                dto.getContent()
        );
    }
}