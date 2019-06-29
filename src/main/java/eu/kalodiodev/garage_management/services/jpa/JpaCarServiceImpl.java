package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.NotFoundException;
import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.converter.CarCommandToCar;
import eu.kalodiodev.garage_management.converter.CarToCarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.repositories.CarRepository;
import eu.kalodiodev.garage_management.repositories.CustomerRepository;
import eu.kalodiodev.garage_management.services.CarService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaCarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final CarCommandToCar carCommandToCar;
    private final CarToCarCommand carToCarCommand;

    public JpaCarServiceImpl(CarRepository carRepository,
                             CustomerRepository customerRepository,
                             CarCommandToCar carCommandToCar,
                             CarToCarCommand carToCarCommand) {

        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.carCommandToCar = carCommandToCar;
        this.carToCarCommand = carToCarCommand;
    }

    @Override
    public Car save(CarCommand carCommand) {
        return carRepository.save(carCommandToCar.convert(carCommand));
    }

    @Override
    public Car findById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);

        if (carOptional.isEmpty()) {
            throw new NotFoundException("Car not found");
        }

        return carOptional.get();
    }

    @Override
    public CarCommand findCommandById(Long id) {

        return carToCarCommand.convert(findById(id));
    }

    @Override
    public void update(CarCommand carCommand) {
        findById(carCommand.getId());

        carRepository.save(carCommandToCar.convert(carCommand));
    }

    @Override
    public void delete(Long customerId, Long carId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            Optional<Car> carOptional = customer.getCars()
                    .stream()
                    .filter(car -> car.getId().equals(carId))
                    .findFirst();

            if (carOptional.isPresent()) {
                Car car = carOptional.get();
                car.setCustomer(null);
                customer.getCars().remove(car);

                carRepository.delete(car);
            }
        }
    }

    @Override
    public Car findByCustomerIdAndCarId(Long customerId, Long carId) {
        Optional<Car> carOptional = carRepository.findCarByIdAndCustomerId(carId, customerId);

        if (carOptional.isEmpty()) {
            throw new NotFoundException("Car not found for given customer");
        }

        return carOptional.get();
    }

    @Override
    public CarCommand findCommandByCustomerIdAndCarId(Long customerId, Long carId) {
        return carToCarCommand.convert(findByCustomerIdAndCarId(customerId, carId));
    }
}
