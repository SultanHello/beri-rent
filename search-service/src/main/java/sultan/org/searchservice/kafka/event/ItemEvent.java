package sultan.org.searchservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemEvent {
    private String id;
    private UUID ownerId;
    private String title;
    private String description;
    private int pricePerDay;
    private String itemStatus;
    private String category;
    private String city;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
}