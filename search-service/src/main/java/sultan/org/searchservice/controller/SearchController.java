import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/items")
    public Page<ItemDocument> searchItems(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Double radius,
            @RequestParam(defaultValue = "price_asc") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return searchService.search(
                q, category, city,
                minPrice, maxPrice,
                lat, lng, radius,
                sortBy, page, size
        );
    }

    @GetMapping("/items/autocomplete")
    public List<String> autocomplete(@RequestParam String q) {
        return searchService.autocomplete(q);
    }

    @GetMapping("/items/nearby")
    public Page<ItemDocument> nearby(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "10") Double radius,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return searchService.nearby(lat, lng, radius, page, size);
    }

    @GetMapping("/facets")
    public Map<String, Object> facets() {
        return searchService.getFacets();
    }
}