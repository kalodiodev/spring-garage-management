package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CustomerToCustomerCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String NAME_VALUE = "John Doe";
    private static final String ADDRESS_VALUE = "Wall Street";
    private static final String CITY_VALUE = "New York";
    private static final String POSTCODE_VALUE = "10900";
    private static final String COUNTRY_VALUE = "US";
    private static final String EMAIL_VALUE = "john@example.com";
    private static final String PHONE_VALUE = "21113393";

    private static final Long CAR_ID_VALUE = 1L;

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
        customer.setId(ID_VALUE);
        customer.setName(NAME_VALUE);
        customer.setAddress(ADDRESS_VALUE);
        customer.setCity(CITY_VALUE);
        customer.setPostcode(POSTCODE_VALUE);
        customer.setCountry(COUNTRY_VALUE);
        customer.setEmail(EMAIL_VALUE);
        customer.setPhone(PHONE_VALUE);

        Car car = new Car();
        car.setId(CAR_ID_VALUE);

        customer.getCars().add(car);

        // when
        CustomerCommand command = converter.convert(customer);

        // then
        assertNotNull(command);
        assertNotNull(customer);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(NAME_VALUE, command.getName());
        assertEquals(ADDRESS_VALUE, command.getAddress());
        assertEquals(CITY_VALUE, command.getCity());
        assertEquals(POSTCODE_VALUE, command.getPostcode());
        assertEquals(COUNTRY_VALUE, command.getCountry());
        assertEquals(EMAIL_VALUE, command.getEmail());
        assertEquals(PHONE_VALUE, command.getPhone());
        assertEquals(1, command.getCars().size());
    }
}
