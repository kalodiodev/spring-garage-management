package eu.kalodiodev.garage_management.bootstrap;

import eu.kalodiodev.garage_management.converter.CarToCarCommand;
import eu.kalodiodev.garage_management.converter.CustomerToCustomerCommand;
import eu.kalodiodev.garage_management.converter.VisitToVisitCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.domains.Visit;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.CustomerService;
import eu.kalodiodev.garage_management.services.VisitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerService customerService;
    private final CarService carService;
    private final VisitService visitService;
    private final CustomerToCustomerCommand customerToCustomerCommand;
    private final CarToCarCommand carToCarCommand;
    private final VisitToVisitCommand visitToVisitCommand;

    public DataLoader(CustomerService customerService,
                      CarService carService,
                      VisitService visitService,
                      CustomerToCustomerCommand customerToCustomerCommand,
                      CarToCarCommand carToCarCommand,
                      VisitToVisitCommand visitToVisitCommand) {

        this.customerService = customerService;
        this.carService = carService;
        this.visitService = visitService;
        this.customerToCustomerCommand = customerToCustomerCommand;
        this.carToCarCommand = carToCarCommand;
        this.visitToVisitCommand = visitToVisitCommand;
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
        customer = customerService.save(customerToCustomerCommand.convert(customer));

        Car car = new Car();
        car.setNumberPlate("AAA-1234");
        car.setManufacturer("Nissan");
        car.setManufacturedYear(2018);
        car.setModel("Pathfinder");
        car.setCustomer(customer);

        Visit visit = new Visit();
        visit.setDescription("My Description");
        visit.setDate(LocalDate.of(2019, 07, 05));

        car.getVisits().add(visit);

        Car savedCar = carService.save(carToCarCommand.convert(car));

        visit.setCar(savedCar);
        visitService.save(visitToVisitCommand.convert(visit));
    }
}
