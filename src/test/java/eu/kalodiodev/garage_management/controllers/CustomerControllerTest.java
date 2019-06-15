package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mockMvc;

    private List<Customer> customerList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setId(1L);

        customerList.add(customer);

        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .build();
    }

    @Test
    void indexCustomers() throws Exception {
        when(customerService.all()).thenReturn(customerList);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/index"))
                .andExpect(model().attribute("customers", hasSize(1)));
    }

    @Test
    void initCreateCustomer() throws Exception {
        mockMvc.perform(get("/customers/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/create"))
                .andExpect(model().attributeExists("customerCommand"));
    }

    @Test
    void storeCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerService.save(any(CustomerCommand.class))).thenReturn(customer);

        mockMvc.perform(post("/customers").param("name", "John Doe"))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers"));
    }
}