package sultan.org.itemservice.item.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import sultan.org.itemservice.item.enums.ItemStatus;
import sultan.org.itemservice.item.exceptions.ItemNotFoundException;
import sultan.org.itemservice.item.exceptions.NotOwnerException;
import sultan.org.itemservice.item.model.dto.ItemRequestDto;
import sultan.org.itemservice.item.model.entity.Item;
import sultan.org.itemservice.item.repository.ItemRepository;
import sultan.org.itemservice.item.service.ItemService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    @Override
    public void createItem(ItemRequestDto itemRequestDto, UUID ownerId) {
        itemRequestDto.setOwnerId(ownerId);
        itemRepository.save(getBuildItemFromDto(itemRequestDto));
    }

    @Override
    public void publish(Long itemId, Jwt ownerId) throws NotOwnerException {
        Item item = itemRepository.findById(itemId).orElseThrow(()->new ItemNotFoundException("item not found"));
        if(item.getOwnerId().equals(ownerId)){
            item.setItemStatus(ItemStatus.ACTIVE);
            itemRepository.save(item);
        }else{
             throw new NotOwnerException("not owner");
        }


    }

    private static Item getBuildItemFromDto(ItemRequestDto itemRequestDto) {
        return Item.builder()
                .id(itemRequestDto.getId())
                .createdAt(itemRequestDto.getCreatedAt())
                .itemStatus(ItemStatus.DRAFT)
                .title(itemRequestDto.getTitle())
                .description(itemRequestDto.getDescription())
                .price_per_day(itemRequestDto.getPrice_per_day())
                .ownerId(itemRequestDto.getOwnerId())
                .build();
    }
}
