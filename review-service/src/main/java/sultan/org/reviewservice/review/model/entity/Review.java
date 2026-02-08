package sultan.org.reviewservice.review.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"bookingId", "authorId"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    private Long itemId;

    private UUID authorId;     // кто написал
    private UUID targetUserId; // о ком отзыв

    @Column(length = 1000)
    private String text;

    private int rating; // 1..5

    private boolean helpful;

    private String ownerResponse;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}