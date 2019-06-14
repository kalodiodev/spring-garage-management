package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.repositories.CustomerRepository;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaCustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public JpaCustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> all() {
        return customerRepository.findAll();
    }
}
