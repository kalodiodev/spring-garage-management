package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CustomerService;
import net.bytebuddy.utility.RandomString;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void displayCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerService.findById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/show"))
                .andExpect(model().attribute("customer", customer));
    }

    @Test
    void displayCustomerNotFound() throws Exception {
        when(customerService.findById(anyLong())).thenThrow(new NotFoundException("Customer not found"));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().is4xxClientError());
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
                .andExpect(view().name("redirect:/customers/" + customer.getId()))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void storeCustomerValidateName() throws Exception {
        mockMvc.perform(post("/customers").param("name", ""))
                .andExpect(model().attributeHasFieldErrors("customerCommand", "name"));

        mockMvc.perform(post("/customers").param("name", RandomString.make(257)))
                .andExpect(model().attributeHasFieldErrors("customerCommand", "name"));

        mockMvc.perform(post("/customers").param("name", RandomString.make(2)))
                .andExpect(model().attributeHasFieldErrors("customerCommand", "name"));
    }

    @Test
    void initUpdateCustomer() throws Exception {
        CustomerCommand customerCommand = new CustomerCommand();
        customerCommand.setId(1L);

        when(customerService.findCommandById(1L)).thenReturn(customerCommand);

        mockMvc.perform(get("/customers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/edit"))
                .andExpect(model().attributeExists("customerCommand"));
    }

    @Test
    void editCustomerNotFound() throws Exception {
        when(customerService.findCommandById(anyLong())).thenThrow(new NotFoundException("Customer not found"));

        mockMvc.perform(get("/customers/1/edit"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateCustomer() throws Exception {
        mockMvc.perform(patch("/customers/1")
                .param("name", "Jane Doe")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1"))
                .andExpect(flash().attributeExists("message"));

        verify(customerService).update(any(CustomerCommand.class));
    }

    @Test
    void updateCustomerValidateName() throws Exception {
        mockMvc.perform(patch("/customers/1").param("name", ""))
                .andExpect(model().attributeHasFieldErrors("customerCommand", "name"));

        mockMvc.perform(patch("/customers/1").param("name", RandomString.make(257)))
                .andExpect(model().attributeHasFieldErrors("customerCommand", "name"));

        mockMvc.perform(patch("/customers/1").param("name", RandomString.make(2)))
                .andExpect(model().attributeHasFieldErrors("customerCommand", "name"));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers"))
                .andExpect(flash().attributeExists("message"));

        verify(customerService).delete(1L);
    }

}