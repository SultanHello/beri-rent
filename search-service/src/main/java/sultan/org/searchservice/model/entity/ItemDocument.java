package sultan.org.searchservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.time.LocalDateTime;
import java.util.UUID;


@Document(indexName = "items", createIndex = true)
@Setting(settingPath = "/elasticsearch/settings.json")
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

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;


    @GeoPointField
    private GeoPoint location; // если будешь делать nearby поиск
}