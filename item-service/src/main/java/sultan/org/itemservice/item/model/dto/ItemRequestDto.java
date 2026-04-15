package sultan.org.itemservice.item.model.dto;

import lombok.Getter;
import lombok.Setter;
import sultan.org.itemservice.item.enums.ItemStatus;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class ItemRequestDto {
    private Long id;
    private UUID ownerId;
    private String title;
    private String description;
    private int pricePerDay;
    private LocalDateTime createdAt;
    private String category;
    private String city;

    // Геолокация
    private Double latitude;
    private Double longitude;
}
