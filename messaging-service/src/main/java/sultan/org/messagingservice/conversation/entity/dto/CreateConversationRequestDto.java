package sultan.org.messagingservice.conversation.entity.dto;

import lombok.Data;
import sultan.org.messagingservice.conversation.model.entity.Conversation;

import java.util.UUID;

@Data
public class CreateConversationRequestDto {
    private Long bookingId;
    private UUID ownerId;
    private UUID renterId;
    public static CreateConversationRequestDto fromEntity(Conversation entity) {
        if (entity == null) {
            return null;
        }
        CreateConversationRequestDto dto = new CreateConversationRequestDto();
        dto.setBookingId(entity.getBookingId());
        dto.setOwnerId(entity.getOwnerId());
        dto.setRenterId(entity.getRenterId());
        return dto;
    }
}