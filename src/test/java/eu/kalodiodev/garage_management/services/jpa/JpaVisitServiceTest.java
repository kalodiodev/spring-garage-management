package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.converter.VisitCommandToVisit;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaVisitServiceTest {

    @Mock
    VisitRepository visitRepository;

    @Mock
    VisitCommandToVisit visitCommandToVisit;

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
}
