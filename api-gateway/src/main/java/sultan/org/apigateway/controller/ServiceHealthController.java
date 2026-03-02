package com.example.gateway.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actuator")
@RequiredArgsConstructor
public class ServiceHealthController {

    private final DiscoveryClient discoveryClient;

    @GetMapping("/health/services")
    public Map<String, Object> servicesHealth() {

        Map<String, Object> response = new HashMap<>();

        List<String> services = discoveryClient.getServices();

        response.put("registeredServices", services);
        response.put("count", services.size());

        return response;
    }
}