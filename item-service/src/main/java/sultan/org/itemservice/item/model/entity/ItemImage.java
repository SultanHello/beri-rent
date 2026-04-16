package sultan.org.itemservice.item.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_images")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isMain;
}