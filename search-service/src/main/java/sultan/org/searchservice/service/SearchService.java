import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchResponse searchItems(SearchRequest request) {

        // Создаем критерии поиска
        CriteriaQuery criteriaQuery= new CriteriaQuery(buildCriteria(request));

        // Сортировка
        query.addSort(buildSort(request.getSortBy()));

        // Пагинация
        query.setPageable(PageRequest.of(request.getPage(), request.getSize()));

        // Выполняем поиск
        SearchHits<ItemDocument> searchHits = elasticsearchOperations.search(
                query,
                ItemDocument.class
        );

        // Преобразуем результаты
        List<ItemDTO> items = searchHits.getSearchHits().stream()
                .map(hit -> mapToDTO(hit.getContent()))
                .collect(Collectors.toList());

        return SearchResponse.builder()
                .items(items)
                .total(searchHits.getTotalHits())
                .page(request.getPage())
                .size(request.getSize())
                .totalPages((int) Math.ceil(searchHits.getTotalHits() / (double) request.getSize()))
                .build();
    }

    private Criteria buildCriteria(SearchRequest request) {
        Criteria criteria = new Criteria();

        // Текстовый поиск (по title и description)
        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            criteria = criteria.and(
                    new Criteria("title").contains(request.getQuery())
                            .or(new Criteria("description").contains(request.getQuery()))
            );
        }

        // Фильтр по цене
        if (request.getMinPrice() != null) {
            criteria = criteria.and(new Criteria("pricePerDay").greaterThanEqual(request.getMinPrice()));
        }
        if (request.getMaxPrice() != null) {
            criteria = criteria.and(new Criteria("pricePerDay").lessThanEqual(request.getMaxPrice()));
        }

        // Фильтр по статусу
        if (request.getStatus() != null) {
            criteria = criteria.and(new Criteria("itemStatus").is(request.getStatus()));
        } else {
            // По умолчанию показываем только ACTIVE
            criteria = criteria.and(new Criteria("itemStatus").is("ACTIVE"));
        }

        return criteria;
    }

    private Sort buildSort(String sortBy) {
        return switch (sortBy) {
            case "price_asc" -> Sort.by(Sort.Direction.ASC, "pricePerDay");
            case "price_desc" -> Sort.by(Sort.Direction.DESC, "pricePerDay");
            case "newest" -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            default -> Sort.by(Sort.Direction.DESC, "_score"); // по релевантности
        };
    }

    private ItemDTO mapToDTO(ItemDocument doc) {
        return ItemDTO.builder()
                .id(doc.getItemId())
                .ownerId(UUID.fromString(doc.getOwnerId()))
                .title(doc.getTitle())
                .description(doc.getDescription())
                .pricePerDay(doc.getPricePerDay())
                .itemStatus(doc.getItemStatus())
                .createdAt(doc.getCreatedAt())
                .ownerName(doc.getOwner() != null ? doc.getOwner().getOwnerName() : null)
                .ownerRating(doc.getOwner() != null ? doc.getOwner().getOwnerRating() : null)
                .build();
    }
}