package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarToCarCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String NUMBER_PLATE_VALUE = "AAA-1234";
    private static final String MANUFACTURER_VALUE = "Nissan";
    private static final String MODEL_VALUE = "Pathfinder";
    private static final int MANUFACTURED_YEAR_VALUE = 2015;
    private static final Long CUSTOMER_ID_VALUE = 1L;

    private CarToCarCommand converter;

    @Before
    public void setUp()throws Exception {
        converter = new CarToCarCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Car()));
    }

    @Test
    public void convert() throws Exception {
        // given
        Car car = new Car();
        car.setId(ID_VALUE);
        car.setNumberPlate(NUMBER_PLATE_VALUE);
        car.setManufacturer(MANUFACTURER_VALUE);
        car.setModel(MODEL_VALUE);
        car.setManufacturedYear(MANUFACTURED_YEAR_VALUE);

        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID_VALUE);
        car.setCustomer(customer);

        // when
        CarCommand command = converter.convert(car);

        // then
        assertNotNull(command);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(NUMBER_PLATE_VALUE, command.getNumberPlate());
        assertEquals(MANUFACTURER_VALUE, command.getManufacturer());
        assertEquals(MODEL_VALUE, command.getModel());
        assertEquals(MANUFACTURED_YEAR_VALUE, command.getManufacturedYear());
        assertEquals(CUSTOMER_ID_VALUE, command.getCustomerId());
    }
}
