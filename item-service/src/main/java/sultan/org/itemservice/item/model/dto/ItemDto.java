package sultan.org.itemservice.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sultan.org.itemservice.item.model.entity.Item;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private UUID ownerId;
    private String title;
    private String description;
    private int price_per_day;
    private LocalDateTime createdAt;

    public static ItemDto fromEntity(Item item){
        return ItemDto.builder()
                .id(item.getId())
                .ownerId(item.getOwnerId())
                .title(item.getTitle())
                .description(item.getDescription())
                .price_per_day(item.getPrice_per_day())
                .createdAt(item.getCreatedAt()).build();
    }
}
