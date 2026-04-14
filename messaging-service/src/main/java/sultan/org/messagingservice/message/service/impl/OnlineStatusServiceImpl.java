package sultan.org.messagingservice.message.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OnlineStatusServiceImpl {

    private final RedisTemplate<String, Object> redisTemplate;

    // сохранить что пользователь онлайн
    public void setOnline(UUID userId) {
        redisTemplate.opsForValue().set(
            "user:" + userId + ":online",  // ключ
            true,                           // значение
            5, TimeUnit.MINUTES             // TTL
        );
    }

    // проверить онлайн ли пользователь
    public boolean isOnline(UUID userId) {
        return Boolean.TRUE.equals(
            redisTemplate.opsForValue().get("user:" + userId + ":online")
        );
    }

    // удалить (пользователь офлайн)
    public void setOffline(UUID userId) {
        redisTemplate.delete("user:" + userId + ":online");
    }
}