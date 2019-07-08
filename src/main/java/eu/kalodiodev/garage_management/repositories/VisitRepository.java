package eu.kalodiodev.garage_management.repositories;

import eu.kalodiodev.garage_management.domains.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    Optional<Visit> findVisitByIdAndCarIdAndCarCustomerId(Long id, Long carId, Long customerId);
}
