package sultan.org.searchservice.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;

import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import sultan.org.searchservice.model.entity.ItemDocument;


import java.util.HashMap;
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

        NativeQueryBuilder builder = new NativeQueryBuilder();

        builder.withQuery(qb -> qb.bool(b -> {

            // 🔎 FULL TEXT SEARCH
            if (q != null && !q.isBlank()) {
                b.must(m -> m.multiMatch(mm -> mm
                        .query(q)
                        .fields("title", "description")
                ));
            }

            // 📂 CATEGORY
            if (category != null && !category.isBlank()) {
                b.filter(f -> f.term(t -> t
                        .field("category")
                        .value(category)
                ));
            }

            // 🏙 CITY
            if (city != null && !city.isBlank()) {
                b.filter(f -> f.term(t -> t
                        .field("city")
                        .value(city)
                ));
            }

            // 💰 PRICE RANGE
            if (minPrice != null || maxPrice != null) {
                b.filter(f -> f.range(r -> {
                    r.field("pricePerDay");

                    if (minPrice != null) {
                        r.gte(JsonData.of(minPrice));
                    }
                    if (maxPrice != null) {
                        r.lte(JsonData.of(maxPrice));
                    }

                    return r;
                }));
            }

            // 🌍 GEO FILTER
            if (lat != null && lng != null && radius != null) {
                b.filter(f -> f.geoDistance(g -> g
                        .field("location")
                        .location(l -> l.latlon(ll -> ll
                                .lat(lat)
                                .lon(lng)
                        ))
                        .distance(radius + "km")
                ));
            }

            return b;
        }));

        // 📊 SORTING
        if (sortBy != null) {
            switch (sortBy) {
                case "price_asc" ->
                        builder.withSort(s -> s.field(f -> f.field("pricePerDay").order(co.elastic.clients.elasticsearch._types.SortOrder.Asc)));

                case "price_desc" ->
                        builder.withSort(s -> s.field(f -> f.field("pricePerDay").order(co.elastic.clients.elasticsearch._types.SortOrder.Desc)));

                case "newest" ->
                        builder.withSort(s -> s.field(f -> f.field("createdAt").order(co.elastic.clients.elasticsearch._types.SortOrder.Desc)));

                case "nearby" -> {
                    builder.withSort(s -> s.geoDistance(g -> g
                            .field("location")
                            .location(l -> l.latlon(ll -> ll
                                    .lat(lat)
                                    .lon(lng)
                            ))
                            .order(co.elastic.clients.elasticsearch._types.SortOrder.Asc)
                            .unit(co.elastic.clients.elasticsearch._types.DistanceUnit.Kilometers)
                    ));
                }
            }
        }

        builder.withPageable(PageRequest.of(page, size));

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(builder.build(), ItemDocument.class);

        List<ItemDocument> content = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(
                content,
                PageRequest.of(page, size),
                hits.getTotalHits()
        );
    }

    // 📊 FACETS

    public Map<String, Map<String, Long>> getFacets() {
        NativeQuery query = new NativeQueryBuilder()
                .withAggregation("categories", Aggregation.of(a -> a.terms(t -> t.field("category"))))
                .withAggregation("cities", Aggregation.of(a -> a.terms(t -> t.field("city"))))
                .withMaxResults(0)
                .build();

        SearchHits<ItemDocument> hits = elasticsearchOperations.search(query, ItemDocument.class);

        // В Spring Data Elasticsearch 5.x агрегации приходят как ElasticsearchAggregations
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) hits.getAggregations();

        if (aggregations == null) {
            return Map.of();
        }

        Map<String, Map<String, Long>> result = new HashMap<>();

        result.put("categories", extractTermBuckets(aggregations, "categories"));
        result.put("cities", extractTermBuckets(aggregations, "cities"));

        return result;
    }

    private Map<String, Long> extractTermBuckets(ElasticsearchAggregations aggregations, String aggName) {
        // 1. Получаем контейнер агрегации по имени
        ElasticsearchAggregation aggregation = aggregations.get(aggName);

        if (aggregation == null) return Map.of();

        // 2. Извлекаем "сырую" агрегацию и приводим её к типу SringTerms (т.к. category и city - строки)
        StringTermsAggregate termsAggregate = aggregation.aggregation().getAggregate().sterms();

        // 3. Собираем Map: "имя_категории" -> "количество_объявлений"
        Map<String, Long> bucketsMap = new HashMap<>();
        for (StringTermsBucket bucket : termsAggregate.buckets().array()) {
            bucketsMap.put(bucket.key().stringValue(), bucket.docCount());
        }

        return bucketsMap;
    }

    // 🔍 AUTOCOMPLETE
    public List<String> autocomplete(String text) {

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(qb -> qb.matchPhrasePrefix(m -> m
                        .field("title")
                        .query(text)
                ))
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(query, ItemDocument.class);

        return hits.stream()
                .map(SearchHit::getContent)
                .map(ItemDocument::getTitle)
                .toList();
    }

    // 📍 NEARBY SEARCH (ОТДЕЛЬНЫЙ ENDPOINT)
    public Page<ItemDocument> nearby(Double lat, Double lng, Double radius, int page, int size) {
        if (lat == null || lng == null) {
            return new PageImpl<>(List.of(), PageRequest.of(page, size), 0);
        }

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(qb -> qb.geoDistance(g -> g
                        .field("location")
                        .location(l -> l.latlon(ll -> ll
                                .lat(lat)
                                .lon(lng)
                        ))
                        .distance(radius + "km")
                ))
                .withSort(s -> s.geoDistance(g -> g
                        .field("location")
                        .location(l -> l.latlon(ll -> ll.lat(lat).lon(lng)))
                        .order(co.elastic.clients.elasticsearch._types.SortOrder.Asc)
                ))
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(query, ItemDocument.class);

        List<ItemDocument> content = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(
                content,
                PageRequest.of(page, size),
                hits.getTotalHits()
        );
    }
}