package eu.kalodiodev.garage_management.bootstrap;

import eu.kalodiodev.garage_management.converter.CarToCarCommand;
import eu.kalodiodev.garage_management.converter.CustomerToCustomerCommand;
import eu.kalodiodev.garage_management.converter.VisitToVisitCommand;
import eu.kalodiodev.garage_management.domains.*;
import eu.kalodiodev.garage_management.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerService customerService;
    private final CarService carService;
    private final VisitService visitService;
    private final UserService userService;
    private final RoleService roleService;
    private final CustomerToCustomerCommand customerToCustomerCommand;
    private final CarToCarCommand carToCarCommand;
    private final VisitToVisitCommand visitToVisitCommand;

    public DataLoader(CustomerService customerService,
                      CarService carService,
                      VisitService visitService,
                      UserService userService, RoleService roleService, CustomerToCustomerCommand customerToCustomerCommand,
                      CarToCarCommand carToCarCommand,
                      VisitToVisitCommand visitToVisitCommand) {

        this.customerService = customerService;
        this.carService = carService;
        this.visitService = visitService;
        this.userService = userService;
        this.roleService = roleService;
        this.customerToCustomerCommand = customerToCustomerCommand;
        this.carToCarCommand = carToCarCommand;
        this.visitToVisitCommand = visitToVisitCommand;
    }

    @Override
    public void run(String... args) throws Exception {

        Role role = new Role();
        role.setName("ADMIN");
        roleService.save(role);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = encoder.encode("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(secret);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.addRole(role);
        userService.save(user);

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
