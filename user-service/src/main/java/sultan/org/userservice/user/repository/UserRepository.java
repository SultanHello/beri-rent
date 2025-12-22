package sultan.org.userservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sultan.org.userservice.user.model.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
