package sultan.org.userservice.userprofile.model.entity;


import jakarta.persistence.*;
import lombok.*;
import sultan.org.userservice.userprofile.model.entity.UserProfile;

@Table(name = "location")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;
    private String country;
    private String city;
    private String address;

}
