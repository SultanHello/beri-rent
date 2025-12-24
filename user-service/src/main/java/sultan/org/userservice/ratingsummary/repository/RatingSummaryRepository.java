package sultan.org.userservice.ratingsummary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.userservice.ratingsummary.model.entity.RatingSummary;


@Repository
public interface RatingSummaryRepository extends JpaRepository<RatingSummary,Long> {
}
