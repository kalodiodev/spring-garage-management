package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerCommandToCustomerTest {

    private static final Long ID_VALUE = 1L;
    private static final String NAME_VALUE = "John Doe";
    private static final String ADDRESS_VALUE = "Wall Street";
    private static final String CITY_VALUE = "New York";
    private static final String POSTCODE_VALUE = "10900";
    private static final String COUNTRY_VALUE = "US";
    private static final String EMAIL_VALUE = "john@example.com";
    private static final String PHONE_VALUE = "21113393";

    private static final Long CAR_ID_VALUE = 1L;

    private CustomerCommandToCustomer converter;

    @Before
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
        command.setId(ID_VALUE);
        command.setName(NAME_VALUE);
        command.setAddress(ADDRESS_VALUE);
        command.setCity(CITY_VALUE);
        command.setPostcode(POSTCODE_VALUE);
        command.setCountry(COUNTRY_VALUE);
        command.setEmail(EMAIL_VALUE);
        command.setPhone(PHONE_VALUE);

        CarCommand carCommand = new CarCommand();
        carCommand.setId(CAR_ID_VALUE);

        command.getCars().add(carCommand);

        // when
        Customer customer = converter.convert(command);

        // then
        assertNotNull(customer);
        assertEquals(ID_VALUE, customer.getId());
        assertEquals(NAME_VALUE, customer.getName());
        assertEquals(ADDRESS_VALUE, customer.getAddress());
        assertEquals(CITY_VALUE, customer.getCity());
        assertEquals(POSTCODE_VALUE, customer.getPostcode());
        assertEquals(COUNTRY_VALUE, customer.getCountry());
        assertEquals(EMAIL_VALUE, customer.getEmail());
        assertEquals(PHONE_VALUE, customer.getPhone());
        assertEquals(1, customer.getCars().size());
    }
}