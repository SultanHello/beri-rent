package sultan.org.searchservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sultan.org.searchservice.kafka.entity.ItemEvent;
import sultan.org.searchservice.model.entity.ItemDocument;

@Service
@RequiredArgsConstructor
public class ItemEventConsumer {

    private final ElasticsearchOperations elasticsearchOperations;

    @KafkaListener(topics = "item-created", groupId = "search-service")
    public void consume(ItemEvent event) {
        elasticsearchOperations.save(ItemDocument.builder()
                .id(event.getId())
                .ownerId(event.getOwnerId())
                .title(event.getTitle())
                .description(event.getDescription())
                .pricePerDay(event.getPricePerDay())
                .itemStatus(event.getItemStatus())
                .createdAt(event.getCreatedAt())
                .build()
        );
    }
}