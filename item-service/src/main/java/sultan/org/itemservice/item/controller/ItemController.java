package sultan.org.itemservice.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import sultan.org.itemservice.item.exceptions.NotOwnerException;
import sultan.org.itemservice.item.model.dto.ItemRequestDto;
import sultan.org.itemservice.item.service.ItemService;

import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;


    @PostMapping("/create")
    public void createItem(@AuthenticationPrincipal Jwt jwt, @RequestBody ItemRequestDto itemRequestDto){
        itemService.createItem(itemRequestDto, UUID.fromString(jwt.getSubject()));
    }

    @PostMapping("/{itemId}/publish")
    public void publishItem(@AuthenticationPrincipal Jwt jwt,@PathVariable Long itemId) throws NotOwnerException {
        itemService.publish(itemId,jwt);

    }
}
