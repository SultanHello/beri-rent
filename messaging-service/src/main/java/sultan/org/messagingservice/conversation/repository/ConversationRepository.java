package sultan.org.messagingservice.conversation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sultan.org.messagingservice.conversation.model.entity.Conversation;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByOwnerIdOrRenterId(UUID ownerId, UUID renterId);
}