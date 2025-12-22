package sultan.org.userservice.user.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID keycloakId;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private RatingSummary ratingSummary;
    @PrePersist
    private void onInsert(){
        this.createdAt=LocalDateTime.now();
    }

}
