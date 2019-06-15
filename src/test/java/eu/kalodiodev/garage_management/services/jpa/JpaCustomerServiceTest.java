package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.converter.CustomerCommandToCustomer;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaCustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerCommandToCustomer customerCommandToCustomer;

    @InjectMocks
    JpaCustomerServiceImpl customerService;

    private List<Customer> customerList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        customerList.add(new Customer());
    }

    @Test
    void find_all_customers() {
        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> customers = customerService.all();

        assertEquals(1, customers.size());
    }

    @Test
    void save_customer_command() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerCommandToCustomer.convert(any(CustomerCommand.class))).thenReturn(new Customer());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        assertEquals(customer, customerService.save(new CustomerCommand()));
    }

    @Test
    void find_customer_by_id() {
        Customer customer = new Customer();
        customer.setId(1L);
        Optional<Customer> optionalCustomer = Optional.of(customer);

        when(customerRepository.findById(anyLong())).thenReturn(optionalCustomer);

        assertEquals(customer, customerService.findById(1L));
    }
}