package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.VisitCommand;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

        when(visitService.findByCustomerIdAndCarIdAndVisitId(1L, 1L, 1L))
                .thenReturn(visit);

        mockMvc.perform(get("/customers/1/cars/1/visits/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("visit/show"))
                .andExpect(model().attribute("visit", visit));
    }

    @Test
    void displayVisitNotFound() throws Exception {
        when(visitService.findByCustomerIdAndCarIdAndVisitId(anyLong(), anyLong(), anyLong()))
                .thenThrow(new NotFoundException("Visit not found"));

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

    @Test
    void storeVisit() throws Exception {
        Car car = new Car();
        car.setId(1L);

        Visit visit = new Visit();
        visit.setId(1L);
        visit.setCar(car);

        when(carService.findByCustomerIdAndCarId(1L, 1L)).thenReturn(car);
        when(visitService.save(any(VisitCommand.class))).thenReturn(visit);

        mockMvc.perform(post("/customers/1/cars/1/visits")
                .param("date", "2019-05-06")
                .param("description", "Our description")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1/cars/1/visits/1"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    void storeVisitValidateDate() throws Exception {
        mockMvc.perform(post("/customers/1/cars/1/visits"))
                .andExpect(model().attributeHasFieldErrors("visitCommand", "date"));

        mockMvc.perform(post("/customers/1/cars/1/visits").param("date", "string"))
                .andExpect(model().attributeHasFieldErrors("visitCommand", "date"));
    }

    @Test
    void storeVisitValidateDescription() throws Exception {
        mockMvc.perform(post("/customers/1/cars/1/visits").param("description", ""))
                .andExpect(model().attributeHasFieldErrors("visitCommand", "description"));
    }

    @Test
    void initEditVisit() throws Exception {

        when(visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(1L, 1L, 1L))
                .thenReturn(new VisitCommand());

        mockMvc.perform(get("/customers/1/cars/1/visits/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("visit/edit"));
    }

    @Test
    void updateVisit() throws Exception {

        mockMvc.perform(patch("/customers/1/cars/1/visits/1")
                .param("date", "2019-05-06")
                .param("description", "Our description")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1/cars/1/visits/1"))
                .andExpect(flash().attributeExists("message"));

        verify(visitService).update(any(VisitCommand.class));
    }

    @Test
    void updateVisitValidateDate() throws Exception {
        mockMvc.perform(patch("/customers/1/cars/1/visits/1"))
                .andExpect(model().attributeHasFieldErrors("visitCommand", "date"));

        mockMvc.perform(patch("/customers/1/cars/1/visits/1").param("date", "string"))
                .andExpect(model().attributeHasFieldErrors("visitCommand", "date"));
    }

    @Test
    void updateVisitValidateDescription() throws Exception {
        mockMvc.perform(patch("/customers/1/cars/1/visits/1").param("description", ""))
                .andExpect(model().attributeHasFieldErrors("visitCommand", "description"));
    }

    @Test
    void deleteVisit() throws Exception {

        mockMvc.perform(delete("/customers/1/cars/1/visits/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/1/cars/1"))
                .andExpect(flash().attributeExists("message"));

        verify(visitService).delete(1L, 1L, 1L);
    }

}
