package eu.kalodiodev.garage_management.repositories;

import eu.kalodiodev.garage_management.domains.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
