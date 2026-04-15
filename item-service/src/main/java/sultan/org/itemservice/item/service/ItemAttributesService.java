package sultan.org.itemservice.item.service;

import java.util.Map;



public interface ItemAttributesService {
    public void save(Long itemId, Map<String, Object> attributes);
    public Map<String, Object> getByItemId(Long itemId);
}
