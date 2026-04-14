package sultan.org.messagingservice.message.service;

import java.util.UUID;

public interface OnlineStatusService {
    public void setOnline(UUID userId);
    public boolean isOnline(UUID userId);
    public void setOffline(UUID userId);
}
