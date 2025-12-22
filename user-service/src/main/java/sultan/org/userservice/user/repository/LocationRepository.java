package sultan.org.userservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.userservice.user.model.entity.Location;



@Repository
public interface LocationRepository extends JpaRepository<Location,Location> {
}
