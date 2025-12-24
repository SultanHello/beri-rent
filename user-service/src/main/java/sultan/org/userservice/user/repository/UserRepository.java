package sultan.org.userservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.userservice.user.model.entity.User;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByKeycloakId(UUID uuid);
    boolean existsByEmail(String email);
}
