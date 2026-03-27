package sultan.org.bookingservice.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sultan.org.bookingservice.booking.model.dto.ItemDto;

import java.util.UUID;

@FeignClient(name = "item-service", path = "/items")
public interface ItemClient {
    @GetMapping("/{id}")
    UUID getItemById(@PathVariable("id") Long id);
}