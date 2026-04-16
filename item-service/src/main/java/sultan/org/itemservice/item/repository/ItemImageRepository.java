package sultan.org.itemservice.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sultan.org.itemservice.item.model.entity.ItemImage;

import java.util.List;
import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findByItemId(Long itemId);
    Optional<ItemImage> findByItemIdAndIsMainTrue(Long itemId);
}