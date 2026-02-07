package sultan.org.messagingservice.message.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.messagingservice.message.model.entity.Message;
import sultan.org.messagingservice.message.repostitory.MessageRepository;
import sultan.org.messagingservice.message.service.MessageService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    @Override
    public Message send(Long conversationId, UUID senderId, String content) {
        Message message = Message.builder()
                .conversationId(conversationId)
                .senderId(senderId)
                .content(content)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(message);
    }

    @Override
    public List<Message> getMessages(Long conversationId) {
        return repository.findByConversationIdOrderByCreatedAtAsc(conversationId);
    }

    @Override
    public void delete(Long messageId, UUID userId) throws AccessDeniedException {
        Message message = repository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getSenderId().equals(userId)) {
            throw new AccessDeniedException("Cannot delete чужое сообщение");
        }

        repository.delete(message);
    }
}