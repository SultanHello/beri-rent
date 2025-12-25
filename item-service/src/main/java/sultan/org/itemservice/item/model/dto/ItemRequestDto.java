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
    private int price_per_day;
    private LocalDateTime createdAt;
}
