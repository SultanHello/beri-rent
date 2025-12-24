package sultan.org.userservice.ratingsummary.model.entity;


import jakarta.persistence.*;
import lombok.*;
import sultan.org.userservice.user.model.entity.User;

@Entity
@Getter
@Setter
@Table(name = "ratings_summary")

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "average_rating")
    private double averageRating;
    @Column(name = "total_reviews")
    private int totalReviews;
    @Column(name = "five_star_count")
    private int fiveStarCount;
    @Column(name = "four_star_count")
    private int fourStarCount;
    @Column(name = "three_star_count")
    private int threeStarCount;
    @Column(name = "two_star_count")
    private int twoStarCount;
    @Column(name = "one_star_count")
    private int oneStarCount;
}
