package eu.kalodiodev.garage_management.bootstrap;

import eu.kalodiodev.garage_management.converter.CustomerToCustomerCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerService customerService;
    private final CarService carService;
    private final CustomerToCustomerCommand customerToCustomerCommand;

    public DataLoader(CustomerService customerService, CarService carService, CustomerToCustomerCommand customerToCustomerCommand) {
        this.customerService = customerService;
        this.carService = carService;
        this.customerToCustomerCommand = customerToCustomerCommand;
    }

    @Override
    public void run(String... args) throws Exception {

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setAddress("Wall Street");
        customer.setCity("New York");
        customer.setPostcode("12345");
        customer.setCountry("US");
        customer.setPhone("1122332423");
        customer.setEmail("test@example.com");
        customer.setComment("A simple comment");

        customerService.save(customerToCustomerCommand.convert(customer));

        Car car = new Car();
        car.setNumberPlate("AAA-1234");
        car.setManufacturer("Nissan");
        car.setManufacturedYear(2018);
        car.setModel("Pathfinder");
        car.setCustomer(customer);
    }
}
