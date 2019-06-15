package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandToCustomer implements Converter<CustomerCommand, Customer> {

    @Override
    public Customer convert(CustomerCommand source) {

        if (source == null) {
            return null;
        }

        final Customer customer = new Customer();
        customer.setId(source.getId());
        customer.setName(source.getName());
        customer.setAddress(source.getAddress());
        customer.setCity(source.getCity());
        customer.setPostcode(source.getPostcode());
        customer.setCountry(source.getCountry());
        customer.setPhone(source.getPhone());
        customer.setEmail(source.getEmail());
        customer.setComment(source.getComment());

        return customer;
    }
}
