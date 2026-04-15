package sultan.org.itemservice.item.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import sultan.org.itemservice.item.enums.ItemStatus;
import sultan.org.itemservice.item.exceptions.ItemNotFoundException;
import sultan.org.itemservice.item.exceptions.NotOwnerException;
import sultan.org.itemservice.item.model.dto.ItemDto;
import sultan.org.itemservice.item.model.dto.ItemRequestDto;
import sultan.org.itemservice.item.model.entity.Item;
import sultan.org.itemservice.item.model.entity.dto.ItemEvent;
import sultan.org.itemservice.item.repository.ItemRepository;
import sultan.org.itemservice.item.service.ItemService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final KafkaTemplate<String, ItemEvent> kafkaTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    @Override
    public void createItem(ItemRequestDto itemRequestDto, UUID ownerId) {
        itemRequestDto.setOwnerId(ownerId);
        Item item = itemRepository.save(getBuildItemFromDto(itemRequestDto));

        // ✅ отправляем в Kafka
        kafkaTemplate.send("item-created",new ItemEvent(
                item.getId().toString(),
                item.getOwnerId(),
                item.getTitle(),
                item.getDescription(),
                item.getPricePerDay(),
                item.getItemStatus().name(),
                item.getCategory(),
                item.getCity(),
                item.getLatitude(),
                item.getLongitude(),
                item.getCreatedAt()

        ));


    }

    @Override
    public void publish(Long itemId, Jwt ownerId) throws NotOwnerException {
        Item item = itemRepository.findById(itemId).orElseThrow(()->new ItemNotFoundException("item not found"));
        UUID UUIDofOwner = UUID.fromString(ownerId.getSubject());
        if(item.getOwnerId().equals(UUIDofOwner)){
            item.setItemStatus(ItemStatus.ACTIVE);
            itemRepository.save(item);
        }else{
             throw new NotOwnerException("not owner");
        }


    }

    @Override
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream().map(ItemDto::fromEntity).toList();
    }

    @Override
    public UUID getOwnerIdByItemId(Long itemId) {
        return itemRepository.findById(itemId).map(Item::getOwnerId).orElseThrow(()->new ItemNotFoundException("item not found"));
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        String key = "item:" + itemId;
        Item cached = (Item) redisTemplate.opsForValue().get(key);
        if (cached != null) return ItemDto.fromEntity(cached);
        Item item = itemRepository.getItemById(itemId).orElseThrow(()->new ItemNotFoundException("item not found"));
        redisTemplate.opsForValue().set(key, item, 10, TimeUnit.MINUTES);
        return ItemDto.fromEntity(item);
    }

    private static Item getBuildItemFromDto(ItemRequestDto itemRequestDto) {
        return Item.builder()
                .id(itemRequestDto.getId())
                .createdAt(itemRequestDto.getCreatedAt())
                .itemStatus(ItemStatus.DRAFT)
                .title(itemRequestDto.getTitle())
                .description(itemRequestDto.getDescription())
                .pricePerDay(itemRequestDto.getPricePerDay())
                .longitude(itemRequestDto.getLongitude())
                .latitude(itemRequestDto.getLatitude())
                .category(itemRequestDto.getCategory())
                .city(itemRequestDto.getCity())
                .ownerId(itemRequestDto.getOwnerId())
                .build();
    }
}
