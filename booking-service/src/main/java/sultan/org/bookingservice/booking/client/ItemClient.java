package sultan.org.bookingservice.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sultan.org.bookingservice.booking.model.dto.ItemDto;

@FeignClient(name = "item-service", path = "/api/items")
public interface ItemClient {
    @GetMapping("/{id}")
    ItemDto getItemById(@PathVariable("id") Long id);
}