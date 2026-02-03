package sultan.org.itemservice.item.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
public class PaymentIntentResponse {
    private String clientSecret;
}