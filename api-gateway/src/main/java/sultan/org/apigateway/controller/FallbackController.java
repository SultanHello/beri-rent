package sultan.org.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user-service")
    public Mono<Map<String, String>> userServiceFallback() {
        return Mono.just(Map.of(
            "error", "User Service is temporarily unavailable",
            "message", "Please try again later"
        ));
    }

    @GetMapping("/item-service")
    public Mono<Map<String, String>> itemServiceFallback() {
        return Mono.just(Map.of(
            "error", "Item Service is temporarily unavailable",
            "message", "Please try again later"
        ));
    }
}