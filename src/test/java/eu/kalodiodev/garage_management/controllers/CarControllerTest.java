package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CarService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void displayCar() throws Exception {
        Car car = new Car();
        car.setId(1L);

        when(carService.findByCustomerIdAndCarId(1L, 1L)).thenReturn(car);

        mockMvc.perform(get("/customers/1/cars/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("car/show"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void initCreateCar() throws Exception {
        when(customerService.findById(1L)).thenReturn(new Customer());

        mockMvc.perform(get("/customers/1/cars/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("car/create"))
                .andExpect(model().attributeExists("carCommand"));
    }

    @Test
    void storeCar() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);

        Car car = new Car();
        car.setId(1L);

        when(customerService.findById(1L)).thenReturn(customer);

        mockMvc.perform(post("/customers/1/cars")
                .param("numberPlate", "AAA-1234")
                .param("manufacturer", "Nissan")
                .param("model", "Pathfinder")
                .param("manufacturedYear", "2018")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void storeCarValidateLicenseNumber() throws Exception {
        mockMvc.perform(post("/customers/1/cars"))
                .andExpect(model().attributeHasFieldErrors("carCommand", "numberPlate"));

        mockMvc.perform(post("/customers/1/cars").param("numberPlate", ""))
                .andExpect(model().attributeHasFieldErrors("carCommand", "numberPlate"));

        mockMvc.perform(post("/customers/1/cars").param("numberPlate", RandomString.make(3)))
                .andExpect(model().attributeHasFieldErrors("carCommand", "numberPlate"));

        mockMvc.perform(post("/customers/1/cars").param("numberPlate", RandomString.make(91)))
                .andExpect(model().attributeHasFieldErrors("carCommand", "numberPlate"));
    }

    @Test
    void storeCarValidateManufacturer() throws Exception {
        mockMvc.perform(post("/customers/1/cars"))
                .andExpect(model().attributeHasFieldErrors("carCommand", "manufacturer"));

        mockMvc.perform(post("/customers/1/cars").param("manufacturer", ""))
                .andExpect(model().attributeHasFieldErrors("carCommand", "manufacturer"));

        mockMvc.perform(post("/customers/1/cars").param("manufacturer", RandomString.make(2)))
                .andExpect(model().attributeHasFieldErrors("carCommand", "manufacturer"));

        mockMvc.perform(post("/customers/1/cars").param("manufacturer", RandomString.make(101)))
                .andExpect(model().attributeHasFieldErrors("carCommand", "manufacturer"));
    }

    @Test
    void storeCarValidateModel() throws Exception {
        mockMvc.perform(post("/customers/1/cars"))
                .andExpect(model().attributeHasFieldErrors("carCommand", "model"));

        mockMvc.perform(post("/customers/1/cars").param("model", ""))
                .andExpect(model().attributeHasFieldErrors("carCommand", "model"));

        mockMvc.perform(post("/customers/1/cars").param("model", RandomString.make(101)))
                .andExpect(model().attributeHasFieldErrors("carCommand", "model"));
    }

    @Test
    void initEditCar() throws Exception {
        CarCommand carCommand = new CarCommand();
        carCommand.setId(1L);
        carCommand.setCustomerId(1L);

        when(carService.findCommandByCustomerIdAndCarId(1L, 1L)).thenReturn(carCommand);

        mockMvc.perform(get("/customers/1/cars/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("car/edit"))
                .andExpect(model().attributeExists("carCommand"));
    }

    @Test
    void updateCar() throws Exception {

        mockMvc.perform(patch("/customers/1/cars/1")
                .param("numberPlate", "AAA-1234")
                .param("manufacturer", "Nissan")
                .param("model", "Pathfinder")
                .param("manufacturedYear", "2001")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1"))
                .andExpect(flash().attributeExists("message"));

        verify(carService).update(any(CarCommand.class));
    }

    @Test
    void deleteCar() throws Exception {

        mockMvc.perform(delete("/customers/1/cars/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1"))
                .andExpect(flash().attributeExists("message"));

        verify(carService).delete(1L,1L);
    }
}