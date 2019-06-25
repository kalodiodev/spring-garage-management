package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    CustomerService customerService;

    @Mock
    CarService carService;

    @InjectMocks
    CarController carController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(carController)
                .build();
    }

    @Test
    void initCreateCar() throws Exception {
        CustomerCommand customerCommand = new CustomerCommand();
        customerCommand.setId(1L);

        when(customerService.findCommandById(1L)).thenReturn(customerCommand);

        mockMvc.perform(get("/customers/1/cars/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("car/create"))
                .andExpect(model().attributeExists("carCommand"));
    }

    @Test
    void storeCar() throws Exception {
        CustomerCommand customerCommand = new CustomerCommand();
        customerCommand.setId(1L);

        Car car = new Car();
        car.setId(1L);

        when(customerService.findCommandById(1L)).thenReturn(customerCommand);

        mockMvc.perform(post("/customers/1/cars"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1"));

    }
}