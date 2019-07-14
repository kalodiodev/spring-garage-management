package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Visit;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class VisitCommandToVisitTest {

    private static final Long ID_VALUE = 1L;
    private static final LocalDate LOCAL_DATE_VALUE = LocalDate.parse("2019-05-14");
    private static final String DESCRIPTION = "My Description";
    private static final Long CAR_ID_VALUE = 1L;

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
        command.setId(ID_VALUE);
        command.setDate(LOCAL_DATE_VALUE);
        command.setDescription(DESCRIPTION);
        command.setCarId(CAR_ID_VALUE);

        // when
        Visit visit = converter.convert(command);

        // then
        assertNotNull(visit);
        assertEquals(ID_VALUE, visit.getId());
        assertEquals(LOCAL_DATE_VALUE, visit.getDate());
        assertEquals(DESCRIPTION, visit.getDescription());
        assertNotNull(visit.getCar());
    }
}
