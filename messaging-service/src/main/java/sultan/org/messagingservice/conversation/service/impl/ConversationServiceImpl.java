package sultan.org.messagingservice.conversation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.messagingservice.conversation.model.entity.Conversation;
import sultan.org.messagingservice.conversation.repository.ConversationRepository;
import sultan.org.messagingservice.conversation.service.ConversationService;
import sultan.org.messagingservice.message.model.entity.Message;
import sultan.org.messagingservice.message.repostitory.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<Conversation> getMyConversations(UUID userId) {
        return conversationRepository.findByOwnerIdOrRenterId(userId, userId);
    }

    @Override
    public Conversation getById(Long conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
    }

    @Override
    public Conversation createInternal(Long bookingId, UUID ownerId, UUID renterId) {
        Conversation conversation = Conversation.builder()
                .bookingId(bookingId)
                .ownerId(ownerId)
                .renterId(renterId)
                .archived(false)
                .createdAt(LocalDateTime.now())
                .build();

        return conversationRepository.save(conversation);
    }

    @Override
    public void archive(Long conversationId, UUID userId) {
        Conversation c = getById(conversationId);
        c.setArchived(true);
        conversationRepository.save(c);
    }

    @Override
    public long getUnreadCount(UUID userId) {
        return conversationRepository.findByOwnerIdOrRenterId(userId, userId)
                .stream()
                .mapToLong(c ->
                        messageRepository.countByConversationIdAndReadFalseAndSenderIdNot(
                                c.getId(), userId
                        )
                )
                .sum();
    }

    @Override
    public void markAsRead(Long conversationId, UUID userId) {
        List<Message> messages =
                messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);

        messages.stream()
                .filter(m -> !m.getSenderId().equals(userId))
                .forEach(m -> m.setRead(true));

        messageRepository.saveAll(messages);
    }
}