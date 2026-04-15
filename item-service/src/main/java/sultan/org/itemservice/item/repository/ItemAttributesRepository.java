package sultan.org.itemservice.item.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sultan.org.itemservice.item.model.entity.ItemAttributes;

import java.util.Optional;

public interface ItemAttributesRepository
    extends MongoRepository<ItemAttributes, String> {
    Optional<ItemAttributes> findByItemId(Long itemId);
}