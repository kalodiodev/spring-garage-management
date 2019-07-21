package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.converter.values.CustomerValues;
import eu.kalodiodev.garage_management.domains.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class CustomerCommandToCustomerTest {

    private static final Long CAR_ID_VALUE = 1L;

    private CustomerCommandToCustomer converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new CustomerCommandToCustomer(new CarCommandToCar());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CustomerCommand()));
    }

    @Test
    public void convert() throws Exception {
        // given
        CustomerCommand command = new CustomerCommand();
        command.setId(CustomerValues.ID_VALUE);
        command.setName(CustomerValues.NAME_VALUE);
        command.setAddress(CustomerValues.ADDRESS_VALUE);
        command.setCity(CustomerValues.CITY_VALUE);
        command.setPostcode(CustomerValues.POSTCODE_VALUE);
        command.setCountry(CustomerValues.COUNTRY_VALUE);
        command.setEmail(CustomerValues.EMAIL_VALUE);
        command.setPhone(CustomerValues.PHONE_VALUE);

        CarCommand carCommand = new CarCommand();
        carCommand.setId(CAR_ID_VALUE);

        command.getCars().add(carCommand);

        // when
        Customer customer = converter.convert(command);

        // then
        assertNotNull(customer);
        assertEquals(CustomerValues.ID_VALUE, customer.getId());
        assertEquals(CustomerValues.NAME_VALUE, customer.getName());
        assertEquals(CustomerValues.ADDRESS_VALUE, customer.getAddress());
        assertEquals(CustomerValues.CITY_VALUE, customer.getCity());
        assertEquals(CustomerValues.POSTCODE_VALUE, customer.getPostcode());
        assertEquals(CustomerValues.COUNTRY_VALUE, customer.getCountry());
        assertEquals(CustomerValues.EMAIL_VALUE, customer.getEmail());
        assertEquals(CustomerValues.PHONE_VALUE, customer.getPhone());
        assertEquals(1, customer.getCars().size());
    }
}