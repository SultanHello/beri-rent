package sultan.org.itemservice.item.service;


import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import sultan.org.itemservice.item.exceptions.NotOwnerException;
import sultan.org.itemservice.item.model.dto.ItemRequestDto;

import java.util.UUID;

@Service
public interface ItemService {
    void createItem(ItemRequestDto itemRequestDto, UUID ownerId);

    void publish(Long itemId, Jwt ownerId) throws NotOwnerException;
}
