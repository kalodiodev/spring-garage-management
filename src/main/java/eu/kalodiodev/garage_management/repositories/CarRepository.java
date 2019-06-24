package eu.kalodiodev.garage_management.repositories;

import eu.kalodiodev.garage_management.domains.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
