package eu.kalodiodev.garage_management.repositories;

import eu.kalodiodev.garage_management.domains.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
