package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.converter.values.CarValues;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarToCarCommandTest {

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
        car.setId(CarValues.ID_VALUE);
        car.setNumberPlate(CarValues.NUMBER_PLATE_VALUE);
        car.setManufacturer(CarValues.MANUFACTURER_VALUE);
        car.setModel(CarValues.MODEL_VALUE);
        car.setManufacturedYear(CarValues.MANUFACTURED_YEAR_VALUE);

        Customer customer = new Customer();
        customer.setId(CarValues.CUSTOMER_ID_VALUE);
        car.setCustomer(customer);

        // when
        CarCommand command = converter.convert(car);

        // then
        assertNotNull(command);
        assertEquals(CarValues.ID_VALUE, command.getId());
        assertEquals(CarValues.NUMBER_PLATE_VALUE, command.getNumberPlate());
        assertEquals(CarValues.MANUFACTURER_VALUE, command.getManufacturer());
        assertEquals(CarValues.MODEL_VALUE, command.getModel());
        assertEquals(CarValues.MANUFACTURED_YEAR_VALUE, command.getManufacturedYear());
        assertEquals(CarValues.CUSTOMER_ID_VALUE, command.getCustomerId());
    }
}
