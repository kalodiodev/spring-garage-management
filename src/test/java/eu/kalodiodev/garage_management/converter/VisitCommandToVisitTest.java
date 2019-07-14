package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.converter.values.VisitValues;
import eu.kalodiodev.garage_management.domains.Visit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VisitCommandToVisitTest {

    private VisitCommandToVisit converter;

    @Before
    public void setUp() throws Exception {
        converter = new VisitCommandToVisit();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new VisitCommand()));
    }

    @Test
    public void convert() throws Exception {
        // given
        VisitCommand command = new VisitCommand();
        command.setId(VisitValues.ID_VALUE);
        command.setDate(VisitValues.LOCAL_DATE_VALUE);
        command.setDescription(VisitValues.DESCRIPTION);
        command.setCarId(VisitValues.CAR_ID_VALUE);

        // when
        Visit visit = converter.convert(command);

        // then
        assertNotNull(visit);
        assertEquals(VisitValues.ID_VALUE, visit.getId());
        assertEquals(VisitValues.LOCAL_DATE_VALUE, visit.getDate());
        assertEquals(VisitValues.DESCRIPTION, visit.getDescription());
        assertNotNull(visit.getCar());
    }
}
