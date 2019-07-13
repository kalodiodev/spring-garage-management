package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarCommandToCarTest {

    private static final Long ID_VALUE = 1L;
    private static final String NUMBER_PLATE_VALUE = "AAA-1234";
    private static final String MANUFACTURER_VALUE = "Nissan";
    private static final String MODEL_VALUE = "Pathfinder";
    private static final int MANUFACTURED_YEAR_VALUE = 2015;
    private static final Long CUSTOMER_ID_VALUE = 1L;

    private CarCommandToCar converter;

    @Before
    public void setUp() throws Exception {
        converter = new CarCommandToCar();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CarCommand()));
    }

    @Test
    public void convert() throws Exception {
        // given
        CarCommand command = new CarCommand();
        command.setId(ID_VALUE);
        command.setNumberPlate(NUMBER_PLATE_VALUE);
        command.setManufacturer(MANUFACTURER_VALUE);
        command.setModel(MODEL_VALUE);
        command.setManufacturedYear(MANUFACTURED_YEAR_VALUE);
        command.setCustomerId(CUSTOMER_ID_VALUE);

        // when
        Car car = converter.convert(command);

        // then
        assertNotNull(car);
        assertEquals(ID_VALUE, car.getId());
        assertEquals(NUMBER_PLATE_VALUE, car.getNumberPlate());
        assertEquals(MANUFACTURER_VALUE, car.getManufacturer());
        assertEquals(MODEL_VALUE, car.getModel());
        assertEquals(MANUFACTURED_YEAR_VALUE, car.getManufacturedYear());
        assertNotNull(car.getCustomer());
    }
}
