package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.converter.CustomerCommandToCustomer;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.repositories.CustomerRepository;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaCustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerCommandToCustomer customerCommandToCustomer;


    public JpaCustomerServiceImpl(CustomerRepository customerRepository,
                                  CustomerCommandToCustomer customerCommandToCustomer) {
        this.customerRepository = customerRepository;
        this.customerCommandToCustomer = customerCommandToCustomer;
    }

    @Override
    public List<Customer> all() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(CustomerCommand customerCommand) {
        return customerRepository.save(customerCommandToCustomer.convert(customerCommand));
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        return customerOptional.orElse(null);
    }
}
