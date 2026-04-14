package sultan.org.messagingservice.webSocket.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import sultan.org.messagingservice.client.UserServiceClient;
import sultan.org.messagingservice.conversation.repository.ConversationRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final ConversationRepository conversationRepository;
    private final UserServiceClient userServiceClient;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // ✅ getAccessor вместо wrap — изменения применяются к оригиналу
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
                message, StompHeaderAccessor.class
        );

        if (accessor == null) return message;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token == null || token.isBlank()) {
                throw new AccessDeniedException("Токен отсутствует");
            }

            UUID userId = userServiceClient.getUserId(token);
            accessor.setUser(() -> userId.toString()); // ✅ теперь сохраняется
        }

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            if (accessor.getUser() == null) {
                throw new AccessDeniedException("Не авторизован");
            }

            UUID userId = UUID.fromString(accessor.getUser().getName());
            Long conversationId = extractConversationId(accessor.getDestination());

            if (conversationId == null) return message;

            if (!conversationRepository.isMember(conversationId, userId)) {
                throw new AccessDeniedException("Нет доступа к этому чату");
            }
        }

        return message;
    }

    private Long extractConversationId(String destination) {
        try {
            String[] parts = destination.split("/");
            return Long.parseLong(parts[parts.length - 1]);
        } catch (Exception e) {
            return null;
        }
    }
}