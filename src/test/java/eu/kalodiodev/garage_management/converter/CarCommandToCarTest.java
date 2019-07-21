package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.converter.values.CarValues;
import eu.kalodiodev.garage_management.domains.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class CarCommandToCarTest {

    private CarCommandToCar converter;

    @BeforeEach
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
        command.setId(CarValues.ID_VALUE);
        command.setNumberPlate(CarValues.NUMBER_PLATE_VALUE);
        command.setManufacturer(CarValues.MANUFACTURER_VALUE);
        command.setModel(CarValues.MODEL_VALUE);
        command.setManufacturedYear(CarValues.MANUFACTURED_YEAR_VALUE);
        command.setCustomerId(CarValues.CUSTOMER_ID_VALUE);

        // when
        Car car = converter.convert(command);

        // then
        assertNotNull(car);
        assertEquals(CarValues.ID_VALUE, car.getId());
        assertEquals(CarValues.NUMBER_PLATE_VALUE, car.getNumberPlate());
        assertEquals(CarValues.MANUFACTURER_VALUE, car.getManufacturer());
        assertEquals(CarValues.MODEL_VALUE, car.getModel());
        assertEquals(CarValues.MANUFACTURED_YEAR_VALUE, car.getManufacturedYear());
        assertNotNull(car.getCustomer());
    }
}
