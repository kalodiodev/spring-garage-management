package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.converter.values.CustomerValues;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CustomerToCustomerCommandTest {

    CustomerToCustomerCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CustomerToCustomerCommand(new CarToCarCommand());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Customer()));
    }

    public void convert() throws Exception {
        // given
        Customer customer = new Customer();
        customer.setId(CustomerValues.ID_VALUE);
        customer.setName(CustomerValues.NAME_VALUE);
        customer.setAddress(CustomerValues.ADDRESS_VALUE);
        customer.setCity(CustomerValues.CITY_VALUE);
        customer.setPostcode(CustomerValues.POSTCODE_VALUE);
        customer.setCountry(CustomerValues.COUNTRY_VALUE);
        customer.setEmail(CustomerValues.EMAIL_VALUE);
        customer.setPhone(CustomerValues.PHONE_VALUE);

        Car car = new Car();
        car.setId(CustomerValues.CAR_ID_VALUE);

        customer.getCars().add(car);

        // when
        CustomerCommand command = converter.convert(customer);

        // then
        assertNotNull(command);
        assertNotNull(customer);
        assertEquals(CustomerValues.ID_VALUE, command.getId());
        assertEquals(CustomerValues.NAME_VALUE, command.getName());
        assertEquals(CustomerValues.ADDRESS_VALUE, command.getAddress());
        assertEquals(CustomerValues.CITY_VALUE, command.getCity());
        assertEquals(CustomerValues.POSTCODE_VALUE, command.getPostcode());
        assertEquals(CustomerValues.COUNTRY_VALUE, command.getCountry());
        assertEquals(CustomerValues.EMAIL_VALUE, command.getEmail());
        assertEquals(CustomerValues.PHONE_VALUE, command.getPhone());
        assertEquals(1, command.getCars().size());
    }
}
