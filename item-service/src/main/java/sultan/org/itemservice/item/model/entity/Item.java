package sultan.org.itemservice.item.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;
import sultan.org.itemservice.item.enums.ItemStatus;
import sultan.org.itemservice.item.service.ItemService;

import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID ownerId;

    private String title;
    private String description;

    private int pricePerDay;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private String category;
    private String city;

    // Геолокация
    private Double latitude;
    private Double longitude;

    private LocalDateTime createdAt;
}