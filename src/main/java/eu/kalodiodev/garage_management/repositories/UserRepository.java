package eu.kalodiodev.garage_management.repositories;

import eu.kalodiodev.garage_management.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
