package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;

import java.util.List;

public interface CustomerService extends CrudService<Customer, CustomerCommand, Long> {

    List<Customer> all();

    void delete(Long id);
}
