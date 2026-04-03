package sultan.org.messagingservice.conversation.entity.dto;

import lombok.Builder;
import lombok.Data;
import sultan.org.messagingservice.conversation.model.entity.Conversation;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ConversationResponseDto {
    private Long id;
    private Long bookingId;
    private UUID ownerId;
    private UUID renterId;
    private LocalDateTime createdAt;

    public static ConversationResponseDto fromEntity(Conversation entity) {
        return ConversationResponseDto.builder()
                .id(entity.getId())
                .bookingId(entity.getBookingId())
                .ownerId(entity.getOwnerId())
                .renterId(entity.getRenterId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}