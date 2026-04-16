package sultan.org.itemservice.item.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sultan.org.itemservice.item.enums.ItemStatus;
import sultan.org.itemservice.item.model.entity.Item;
import sultan.org.itemservice.item.model.entity.ItemImage;

import java.time.LocalDateTime;
import java.util.List;
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
    private ItemStatus itemStatus;
    private String category;
    private String city;

    // Геолокация
    private Double latitude;
    private Double longitude;
    private String mainImageUrl;
    private List<String> additionalImageUrls;
    public static ItemDto fromEntity(Item item){
        return ItemDto.builder()
                .id(item.getId())
                .ownerId(item.getOwnerId())
                .title(item.getTitle())
                .description(item.getDescription())
                .price_per_day(item.getPricePerDay())
                .latitude(item.getLatitude())
                .longitude(item.getLongitude())
                .city(item.getCity())
                .category(item.getCategory())
                .createdAt(item.getCreatedAt())
                .itemStatus(item.getItemStatus())
                .mainImageUrl(
                        item.getImages().stream()
                                .filter(ItemImage::isMain)
                                .map(ItemImage::getImageUrl)
                                .findFirst()
                                .orElse(null)
                )
                .additionalImageUrls(
                        item.getImages().stream()
                                .filter(img -> !img.isMain())
                                .map(ItemImage::getImageUrl)
                                .toList()
                )
                .build();
    }
}
