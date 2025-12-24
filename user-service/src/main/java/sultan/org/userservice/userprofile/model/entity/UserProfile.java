package sultan.org.userservice.userprofile.model.entity;


import jakarta.persistence.*;
import lombok.*;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.user.model.enums.Gender;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String aboutMe;
    @OneToOne(mappedBy = "userProfile",fetch = FetchType.LAZY)
    private Location location;
    private LocalDateTime updatedAt;


}
