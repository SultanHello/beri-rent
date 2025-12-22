package sultan.org.userservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.userservice.user.model.entity.UserProfile;


@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
