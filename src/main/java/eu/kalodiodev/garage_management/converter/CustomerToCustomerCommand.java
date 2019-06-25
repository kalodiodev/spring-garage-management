package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerCommand implements Converter<Customer, CustomerCommand> {

    private final CarToCarCommand carToCarCommand;

    public CustomerToCustomerCommand(CarToCarCommand carToCarCommand) {
        this.carToCarCommand = carToCarCommand;
    }

    @Override
    public CustomerCommand convert(Customer source) {

        if (source == null) {
            return null;
        }

        CustomerCommand customerCommand = new CustomerCommand();
        customerCommand.setId(source.getId());
        customerCommand.setName(source.getName());
        customerCommand.setAddress(source.getAddress());
        customerCommand.setCity(source.getCity());
        customerCommand.setPostcode(source.getPostcode());
        customerCommand.setCountry(source.getCountry());
        customerCommand.setPhone(source.getPhone());
        customerCommand.setEmail(source.getEmail());
        customerCommand.setComment(source.getComment());

        if (source.getCars() != null && source.getCars().size() > 0) {
            source.getCars().forEach(car -> customerCommand.getCars().add(carToCarCommand.convert(car)));
        }

        return customerCommand;
    }

}
