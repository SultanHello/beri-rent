package sultan.org.searchservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sultan.org.searchservice.kafka.event.ItemEvent;
import sultan.org.searchservice.model.entity.ItemDocument;

@Service
@RequiredArgsConstructor
public class ItemEventConsumer {

    private final ElasticsearchOperations elasticsearchOperations;

    @KafkaListener(topics = "item-created", groupId = "search-service")
    public void consume(ItemEvent event) {
        System.out.println("Received event: " + event);

        ItemDocument doc = ItemDocument.builder()
                .id(event.getId())
                .ownerId(event.getOwnerId())
                .title(event.getTitle())
                .description(event.getDescription())
                .pricePerDay(event.getPricePerDay())
                .itemStatus(event.getItemStatus())
                .createdAt(event.getCreatedAt())
                .location(new GeoPoint(event.getLatitude(),event.getLongitude()))
                .city(event.getCity())
                .category(event.getCategory())

                .build();

        System.out.println("Saving document: " + doc);
        elasticsearchOperations.save(doc);
        System.out.println("Saved successfully");
    }
}