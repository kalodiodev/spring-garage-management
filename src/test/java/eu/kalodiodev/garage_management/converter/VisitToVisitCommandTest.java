package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.converter.values.VisitValues;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class VisitToVisitCommandTest {

    private VisitToVisitCommand converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new VisitToVisitCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Visit()));
    }

    @Test
    public void convert() throws Exception {
        // given
        Visit visit = new Visit();
        visit.setId(VisitValues.ID_VALUE);
        visit.setDate(VisitValues.LOCAL_DATE_VALUE);
        visit.setDescription(VisitValues.DESCRIPTION);

        Car car = new Car();
        car.setId(VisitValues.CAR_ID_VALUE);
        visit.setCar(car);

        // when
        VisitCommand command = converter.convert(visit);

        // then
        assertNotNull(command);
        assertEquals(VisitValues.ID_VALUE, command.getId());
        assertEquals(VisitValues.LOCAL_DATE_VALUE, command.getDate());
        assertEquals(VisitValues.DESCRIPTION, command.getDescription());
        assertEquals(VisitValues.CAR_ID_VALUE, command.getCarId());
    }
}
