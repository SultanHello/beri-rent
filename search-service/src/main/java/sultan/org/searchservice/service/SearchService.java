package sultan.org.searchservice.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import sultan.org.searchservice.document.ItemDocument;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public Page<ItemDocument> search(
            String q,
            String category,
            String city,
            Double minPrice,
            Double maxPrice,
            Double lat,
            Double lng,
            Double radius,
            String sortBy,
            int page,
            int size
    ) {

        BoolQuery.Builder boolQuery = QueryBuilders.bool();

        if (q != null) {
            boolQuery.must(QueryBuilders
                    .multiMatch(m -> m
                            .query(q)
                            .fields("title", "description")
                    ));
        }

        if (category != null) {
            boolQuery.filter(QueryBuilders.term(t -> t.field("category").value(category)));
        }

        if (city != null) {
            boolQuery.filter(QueryBuilders.term(t -> t.field("city").value(city)));
        }

        if (minPrice != null || maxPrice != null) {
            boolQuery.filter(QueryBuilders.range(r -> r
                    .field("price")
                    .gte(minPrice != null ? JsonData.of(minPrice) : null)
                    .lte(maxPrice != null ? JsonData.of(maxPrice) : null)
            ));
        }

        if (lat != null && lng != null && radius != null) {
            boolQuery.filter(QueryBuilders.geoDistance(g -> g
                    .field("location")
                    .location(l -> l.latlon(ll -> ll.lat(lat).lon(lng)))
                    .distance(radius + "km")
            ));
        }

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(boolQuery.build()._toQuery())
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(query, ItemDocument.class);

        List<ItemDocument> content = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(content, PageRequest.of(page, size), hits.getTotalHits());
    }

    public List<String> autocomplete(String q) {

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(QueryBuilders.matchPhrasePrefix(m -> m
                        .field("title")
                        .query(q)
                ))
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(query, ItemDocument.class);

        return hits.stream()
                .map(hit -> hit.getContent().getTitle())
                .toList();
    }

    public Page<ItemDocument> nearby(
            Double lat,
            Double lng,
            Double radius,
            int page,
            int size
    ) {

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(QueryBuilders.geoDistance(g -> g
                        .field("location")
                        .location(l -> l.latlon(ll -> ll.lat(lat).lon(lng)))
                        .distance(radius + "km")
                ))
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(query, ItemDocument.class);

        List<ItemDocument> content = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(content, PageRequest.of(page, size), hits.getTotalHits());
    }

    public Map<String, Object> getFacets() {

        NativeQuery query = new NativeQueryBuilder()
                .withAggregation("categories",
                        Aggregation.of(a -> a.terms(t -> t.field("category"))))
                .withAggregation("cities",
                        Aggregation.of(a -> a.terms(t -> t.field("city"))))
                .build();

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(query, ItemDocument.class);

        return hits.getAggregations().asMap();
    }
}