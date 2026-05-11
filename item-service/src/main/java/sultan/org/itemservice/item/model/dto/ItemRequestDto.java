package sultan.org.itemservice.item.model.dto;

import lombok.Data;
import sultan.org.itemservice.item.enums.ItemStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ItemRequestDto {
    private Long id;
    private UUID ownerId;
    private String title;
    private String description;
    private int pricePerDay;
    private ItemStatus itemStatus;
    private String category;
    private String city;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
}