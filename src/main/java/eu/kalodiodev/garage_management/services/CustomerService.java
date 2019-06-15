package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> all();

    Customer save(CustomerCommand customerCommand);

    Customer findById(Long id);
}
