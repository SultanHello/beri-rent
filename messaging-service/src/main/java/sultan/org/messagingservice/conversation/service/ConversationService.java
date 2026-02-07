package sultan.org.messagingservice.conversation.service;

import sultan.org.messagingservice.conversation.model.entity.Conversation;

import java.util.List;
import java.util.UUID;

public interface ConversationService {

    List<Conversation> getMyConversations(UUID userId);

    Conversation getById(Long conversationId);

    Conversation createInternal(Long bookingId, UUID ownerId, UUID renterId);

    void archive(Long conversationId, UUID userId);

    long getUnreadCount(UUID userId);

    void markAsRead(Long conversationId, UUID userId);
}