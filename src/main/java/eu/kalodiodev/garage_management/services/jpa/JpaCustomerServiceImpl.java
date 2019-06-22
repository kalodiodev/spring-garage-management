package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.NotFoundException;
import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.converter.CustomerCommandToCustomer;
import eu.kalodiodev.garage_management.converter.CustomerToCustomerCommand;
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
    private final CustomerToCustomerCommand customerToCustomerCommand;


    public JpaCustomerServiceImpl(CustomerRepository customerRepository,
                                  CustomerCommandToCustomer customerCommandToCustomer,
                                  CustomerToCustomerCommand customerToCustomerCommand) {

        this.customerRepository = customerRepository;
        this.customerCommandToCustomer = customerCommandToCustomer;
        this.customerToCustomerCommand = customerToCustomerCommand;
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

        if (customerOptional.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }

        return customerOptional.get();
    }

    @Override
    public CustomerCommand findCommandById(Long id) {
        return customerToCustomerCommand.convert(findById(id));
    }

    @Override
    public void update(CustomerCommand customerCommand) {
        // Verify customer exists
        findById(customerCommand.getId());

        Customer customer = customerCommandToCustomer.convert(customerCommand);

        customerRepository.save(customer);
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id);

        customerRepository.delete(customer);
    }
}
