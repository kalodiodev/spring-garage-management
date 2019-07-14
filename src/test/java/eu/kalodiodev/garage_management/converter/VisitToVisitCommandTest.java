package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Visit;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class VisitToVisitCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final LocalDate LOCAL_DATE_VALUE = LocalDate.parse("2019-05-14");
    private static final String DESCRIPTION = "My Description";
    private static final Long CAR_ID_VALUE = 1L;

    private VisitToVisitCommand converter;

    @Before
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
        visit.setId(ID_VALUE);
        visit.setDate(LOCAL_DATE_VALUE);
        visit.setDescription(DESCRIPTION);

        Car car = new Car();
        car.setId(CAR_ID_VALUE);
        visit.setCar(car);

        // when
        VisitCommand command = converter.convert(visit);

        // then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(LOCAL_DATE_VALUE, command.getDate());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(CAR_ID_VALUE, command.getCarId());
    }
}
