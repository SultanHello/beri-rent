package sultan.org.searchservice.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(indexName = "items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDocument {

    @Id
    private String id; // В ES лучше String

    private UUID ownerId;

    private String title;

    private String description;

    private int pricePerDay;

    private String itemStatus;

    private LocalDateTime createdAt;

    @GeoPointField
    private GeoPoint location; // если будешь делать nearby поиск
}