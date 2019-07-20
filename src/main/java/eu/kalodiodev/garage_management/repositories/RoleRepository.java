package eu.kalodiodev.garage_management.repositories;

import eu.kalodiodev.garage_management.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
