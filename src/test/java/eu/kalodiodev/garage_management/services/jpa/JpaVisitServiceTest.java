package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.converter.VisitCommandToVisit;
import eu.kalodiodev.garage_management.converter.VisitToVisitCommand;
import eu.kalodiodev.garage_management.domains.Car;
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

    private static final Long VISIT_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;
    private static final Long CAR_ID = 1L;

    private Visit visit;

    @BeforeEach
    void setUp() {
        visit = new Visit();
        visit.setId(VISIT_ID);
    }

    @Test
    void find_visit_by_id() {
        Optional<Visit> visitOptional = Optional.of(visit);

        when(visitRepository.findById(VISIT_ID)).thenReturn(visitOptional);

        assertEquals(visit, visitService.findById(VISIT_ID));
    }

    @Test
    void not_found_visit() {
        when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> visitService.findById(VISIT_ID));
    }

    @Test
    void save_visit_command() {
        when(visitCommandToVisit.convert(any(VisitCommand.class))).thenReturn(new Visit());
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        assertEquals(visit, visitService.save(new VisitCommand()));
    }

    @Test
    void find_visit_by_customer_id_and_car_id_and_visit_id() {
        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(VISIT_ID, CAR_ID, CUSTOMER_ID))
                .thenReturn(Optional.of(visit));

        assertEquals(visit, visitService.findByCustomerIdAndCarIdAndVisitId(CUSTOMER_ID, CAR_ID, VISIT_ID));
    }

    @Test
    void find_visit_command_by_customer_id_and_car_id_and_visit_id() {
        VisitCommand visitCommand = new VisitCommand();
        visitCommand.setId(VISIT_ID);

        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(VISIT_ID, CAR_ID, CUSTOMER_ID))
                .thenReturn(Optional.of(visit));

        when(visitToVisitCommand.convert(visit)).thenReturn(visitCommand);

        assertEquals(
                visitCommand,
                visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(CUSTOMER_ID, CAR_ID, VISIT_ID)
        );
    }

    @Test
    void not_found_visit_command() {
        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(VISIT_ID, CAR_ID, CUSTOMER_ID))
                .thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () ->
            visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(CUSTOMER_ID, CAR_ID, VISIT_ID));
    }

    @Test
    void update_visit() {
        VisitCommand visitCommand = new VisitCommand();
        visitCommand.setId(VISIT_ID);
        visitCommand.setDescription("Test Description");

        when(visitRepository.findById(1L)).thenReturn(Optional.of(new Visit()));
        when(visitCommandToVisit.convert(any(VisitCommand.class))).thenReturn(new Visit());

        visitService.update(visitCommand);

        verify(visitRepository, times(1)).save(any(Visit.class));
    }

    @Test
    void delete_visit() {
        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(VISIT_ID, CAR_ID, CUSTOMER_ID))
                .thenReturn(Optional.of(visit));

        visitService.delete(CUSTOMER_ID, CAR_ID, VISIT_ID);

        verify(visitRepository, times(1)).delete(visit);
    }

    @Test
    void delete_visit_not_found() {
        when(visitRepository.findVisitByIdAndCarIdAndCarCustomerId(VISIT_ID, CAR_ID, CUSTOMER_ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> visitService.delete(CUSTOMER_ID, CAR_ID, VISIT_ID));
    }
}
