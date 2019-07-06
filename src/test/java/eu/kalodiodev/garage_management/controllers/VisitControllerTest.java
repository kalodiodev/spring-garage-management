package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Visit;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Mock
    CarService carService;

    @InjectMocks
    VisitController visitController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }

    @Test
    void displayVisit() throws Exception {
        Visit visit = new Visit();
        visit.setId(1L);

        when(visitService.findById(1L)).thenReturn(visit);

        mockMvc.perform(get("/customers/1/cars/1/visits/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("visit/show"))
                .andExpect(model().attribute("visit", visit));
    }

    @Test
    void displayVisitNotFound() throws Exception {
        when(visitService.findById(anyLong())).thenThrow(new NotFoundException("Visit not found"));

        mockMvc.perform(get("/customers/1/cars/1/visits/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void initCreateVisit() throws Exception {
        Car car = new Car();
        car.setId(1L);

        when(carService.findByCustomerIdAndCarId(1L, 1L)).thenReturn(car);

        mockMvc.perform(get("/customers/1/cars/1/visits/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("visit/create"))
                .andExpect(model().attributeExists("visitCommand"));
    }

}
