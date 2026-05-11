package sultan.org.itemservice.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sultan.org.itemservice.item.model.entity.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> getItemById(Long id);
}