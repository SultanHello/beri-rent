package sultan.org.messagingservice.conversation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sultan.org.messagingservice.conversation.model.entity.Conversation;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByOwnerIdOrRenterId(UUID ownerId, UUID renterId);


    @Query("SELECT COUNT(c) > 0 FROM Conversation c WHERE c.id = :id AND (c.ownerId = :userId OR c.renterId = :userId)")
    boolean isMember(@Param("id") Long id, @Param("userId") UUID userId);

}