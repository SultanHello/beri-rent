package sultan.org.itemservice.item.model.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "item_attributes")
@Data
@NoArgsConstructor
public class ItemAttributes {
    @Id
    private String id;
    private Long itemId;
    private Map<String, Object> attributes;
}