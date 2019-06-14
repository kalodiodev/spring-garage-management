package eu.kalodiodev.garage_management.services.jpa;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaCustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

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
}