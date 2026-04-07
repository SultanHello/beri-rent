package sultan.org.searchservice.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;

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
import sultan.org.searchservice.model.entity.ItemDocument;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

//    public Page<ItemDocument> search(
//            String q,
//            String category,
//            String city,
//            Double minPrice,
//            Double maxPrice,
//            Double lat,
//            Double lng,
//            Double radius,
//            String sortBy,
//            int page,
//            int size
//    ) {
//
//        NativeQuery query = new NativeQueryBuilder()
//                .withQuery(qb -> qb.bool(b -> {
//
//                    // 🔎 Full text
//                    if (q != null && !q.isBlank()) {
//                        b.must(m -> m.multiMatch(mm -> mm
//                                .query(q)
//                                .fields("title", "description")
//                        ));
//                    }
//
//                    // 📂 Category filter (keyword!)
//                    if (category != null && !category.isBlank()) {
//                        b.filter(f -> f.term(t -> t
//                                .field("category.keyword")
//                                .value(category)
//                        ));
//                    }
//
//                    // 🏙 City filter (keyword!)
//                    if (city != null && !city.isBlank()) {
//                        b.filter(f -> f.term(t -> t
//                                .field("city.keyword")
//                                .value(city)
//                        ));
//                    }
//
//                    // 💰 Price range (NEW API)
//                    if (minPrice != null || maxPrice != null) {
//                        b.filter(f -> f.range(r -> {
//                            r.field("price");
//
//                            if (minPrice != null) {
//                                r.gte(JsonData.of(minPrice));
//                            }
//
//                            if (maxPrice != null) {
//                                r.lte(JsonData.of(maxPrice));
//                            }
//
//                            return r;
//                        }));
//                    }
//
//                    // 🌍 Geo distance
//                    if (lat != null && lng != null && radius != null) {
//                        b.filter(f -> f.geoDistance(g -> g
//                                .field("location")
//                                .location(l -> l.latlon(ll -> ll
//                                        .lat(lat)
//                                        .lon(lng)
//                                ))
//                                .distance(radius + "km")
//                        ));
//                    }
//
//                    return b;
//                }))
//                .withPageable(PageRequest.of(page, size))
//                .build();
//
//        SearchHits<ItemDocument> hits =
//                elasticsearchOperations.search(query, ItemDocument.class);
//
//        List<ItemDocument> content = hits.stream()
//                .map(SearchHit::getContent)
//                .toList();
//
//        return new PageImpl<>(
//                content,
//                PageRequest.of(page, size),
//                hits.getTotalHits()
//        );
//    }
public Page<ItemDocument> search(String q, String category, int page, int size) {
    NativeQuery query = new NativeQueryBuilder()
            .withQuery(qb -> qb.bool(b -> {
                if (q != null && !q.isBlank()) {
                    b.must(m -> m.multiMatch(mm -> mm
                            .query(q)
                            .fields("title", "description")
                    ));
                }
                if (category != null && !category.isBlank()) {
                    b.filter(f -> f.term(t -> t
                            .field("category.keyword")
                            .value(category)
                    ));
                }
                return b;
            }))
            .withPageable(PageRequest.of(page, size))
            .build();

    SearchHits<ItemDocument> hits =
            elasticsearchOperations.search(query, ItemDocument.class);

    return new PageImpl<>(
            hits.stream().map(SearchHit::getContent).toList(),
            PageRequest.of(page, size),
            hits.getTotalHits()
    );
}

    public Map<String, Object> getFacets() {

        NativeQuery query = new NativeQueryBuilder()
                .withAggregation("categories",
                        Aggregation.of(a -> a
                                .terms(t -> t
                                        .field("category.keyword")
                                        .size(20)
                                )
                        ))
                .withAggregation("cities",
                        Aggregation.of(a -> a
                                .terms(t -> t
                                        .field("city.keyword")
                                        .size(20)
                                )
                        ))
                .withMaxResults(0)
                .build();

        SearchHits<ItemDocument> hits =
                elasticsearchOperations.search(query, ItemDocument.class);

        return hits.getAggregations() != null
                ? (Map<String, Object>) hits.getAggregations().aggregations()
                : Map.of();
    }

    public List<String> autocomplete(String text){
        return null;


    }

    public Page<ItemDocument> nearby(Double lat, Double lng, Double radius, int page, int size) {
        return null;
    }
}