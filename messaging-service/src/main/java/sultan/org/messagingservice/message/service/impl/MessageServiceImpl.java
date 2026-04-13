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
        System.out.println(">>> send() called: conversationId=" + conversationId + ", senderId=" + senderId);

        Message message = Message.builder()
                .conversationId(conversationId)
                .senderId(senderId)
                .content(content)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        Message saved = repository.save(message);
        System.out.println(">>> saved with id=" + saved.getId());
        return saved;
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