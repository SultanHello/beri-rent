package sultan.org.messagingservice.message.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatMessageDto {
    private UUID senderId;
    private String content;
}