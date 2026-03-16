package sultan.org.userservice.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.userservice.user.model.entity.User;
import sultan.org.userservice.userprofile.model.entity.UserProfile;

import java.util.Optional;


@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    Optional<UserProfile> findUserProfileByUser(User user);
}
