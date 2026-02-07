package sultan.org.messagingservice.message.service;

import sultan.org.messagingservice.message.model.entity.Message;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface MessageService {

    Message send(Long conversationId, UUID senderId, String content);

    List<Message> getMessages(Long conversationId);

    void delete(Long messageId, UUID userId) throws AccessDeniedException;
}
