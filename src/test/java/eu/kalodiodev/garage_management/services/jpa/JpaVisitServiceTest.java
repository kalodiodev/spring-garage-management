package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.converter.VisitCommandToVisit;
import eu.kalodiodev.garage_management.converter.VisitToVisitCommand;
import eu.kalodiodev.garage_management.domains.Visit;
import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JpaVisitServiceTest {

    @Mock
    VisitRepository visitRepository;

    @Mock
    VisitCommandToVisit visitCommandToVisit;

    @Mock
    VisitToVisitCommand visitToVisitCommand;

    @InjectMocks
    JpaVisitServiceImpl visitService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void find_visit_by_id() {
        Visit visit = new Visit();
        visit.setId(1L);
        Optional<Visit> visitOptional = Optional.of(visit);

        when(visitRepository.findById(1L)).thenReturn(visitOptional);

        assertEquals(visit, visitService.findById(1L));
    }

    @Test
    void not_found_visit() {
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> visitService.findById(1L));
    }

    @Test
    void save_visit_command() {
        Visit visit = new Visit();
        visit.setId(1L);

        when(visitCommandToVisit.convert(any(VisitCommand.class))).thenReturn(new Visit());
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        assertEquals(visit, visitService.save(new VisitCommand()));
    }

    @Test
    void find_visit_by_customer_id_and_car_id_and_visit_id() {
        Visit visit = new Visit();
        visit.setId(1L);

        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(1L, 1L, 1L))
                .thenReturn(Optional.of(visit));

        assertEquals(visit, visitService.findByCustomerIdAndCarIdAndVisitId(1L, 1L, 1L));
    }

    @Test
    void find_visit_command_by_customer_id_and_car_id_and_visit_id() {
        Visit visit = new Visit();
        visit.setId(1L);

        VisitCommand visitCommand = new VisitCommand();
        visitCommand.setId(1L);


        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(1L, 1L, 1L))
                .thenReturn(Optional.of(visit));

        when(visitToVisitCommand.convert(visit)).thenReturn(visitCommand);

        assertEquals(
                visitCommand,
                visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(1L, 1L, 1L)
        );
    }

    @Test
    void not_found_visit_command() {
        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(1L, 1L, 1L))
                .thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () ->
            visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(1L, 1L, 1L));
    }

    @Test
    void update_visit() {
        VisitCommand visitCommand = new VisitCommand();
        visitCommand.setId(1L);
        visitCommand.setDescription("Test Description");

        when(visitRepository.findById(1L)).thenReturn(Optional.of(new Visit()));
        when(visitCommandToVisit.convert(any(VisitCommand.class))).thenReturn(new Visit());

        visitService.update(visitCommand);

        verify(visitRepository, times(1)).save(any(Visit.class));
    }
}
