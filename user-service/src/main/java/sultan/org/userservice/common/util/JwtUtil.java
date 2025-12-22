package sultan.org.userservice.common.util;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.UUID;

public class JwtUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static UUID extractSubject(String token) {
        try {
            // Убираем "Bearer " если есть
            String actualToken = token.replace("Bearer ", "");

            // JWT состоит из 3 частей: header.payload.signature
            String[] parts = actualToken.split("\\.");

            // Декодируем payload (вторая часть)
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            // Парсим JSON и извлекаем subject
            JsonNode claims = objectMapper.readTree(payload);
            String subject = claims.get("sub").asText();

            return UUID.fromString(subject);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract subject from JWT", e);
        }
    }
}