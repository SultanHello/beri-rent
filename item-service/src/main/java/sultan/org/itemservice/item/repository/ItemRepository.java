package sultan.org.itemservice.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.itemservice.item.model.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
