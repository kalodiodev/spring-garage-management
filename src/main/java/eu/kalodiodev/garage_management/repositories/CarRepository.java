package eu.kalodiodev.garage_management.repositories;

import eu.kalodiodev.garage_management.domains.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findCarByIdAndCustomerId(Long carId, Long customerId);
}
