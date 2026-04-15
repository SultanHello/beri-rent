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
    private String id;

    private UUID ownerId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Integer)
    private Integer pricePerDay;

    @Field(type = FieldType.Keyword)
    private String itemStatus;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String city;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute)
    private LocalDateTime createdAt;

    @GeoPointField
    private GeoPoint location;
}