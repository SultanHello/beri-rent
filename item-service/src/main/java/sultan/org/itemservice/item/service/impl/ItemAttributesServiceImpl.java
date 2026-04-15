package sultan.org.itemservice.item.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.itemservice.item.model.entity.ItemAttributes;
import sultan.org.itemservice.item.repository.ItemAttributesRepository;
import sultan.org.itemservice.item.service.ItemAttributesService;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemAttributesServiceImpl implements ItemAttributesService {

    private final ItemAttributesRepository itemAttributesRepository;

    public void save(Long itemId, Map<String, Object> attributes) {
        ItemAttributes itemAttributes = itemAttributesRepository
                .findByItemId(itemId)
                .orElse(new ItemAttributes());

        itemAttributes.setItemId(itemId);
        itemAttributes.setAttributes(attributes);

        itemAttributesRepository.save(itemAttributes);
    }

    public Map<String, Object> getByItemId(Long itemId) {
        return itemAttributesRepository
                .findByItemId(itemId)
                .map(ItemAttributes::getAttributes)
                .orElse(Collections.emptyMap());
    }
}