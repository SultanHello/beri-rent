package sultan.org.messagingservice.message.repostitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sultan.org.messagingservice.conversation.model.entity.Conversation;
import sultan.org.messagingservice.message.model.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationIdOrderByCreatedAtAsc(Long conversationId);

    long countByConversationIdAndReadFalseAndSenderIdNot(
            Long conversationId,
            UUID userId
    );
    }