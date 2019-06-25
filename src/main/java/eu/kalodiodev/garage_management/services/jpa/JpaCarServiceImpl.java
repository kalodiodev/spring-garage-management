package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.converter.CarCommandToCar;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.repositories.CarRepository;
import eu.kalodiodev.garage_management.services.CarService;
import org.springframework.stereotype.Service;

@Service
public class JpaCarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarCommandToCar carCommandToCar;

    public JpaCarServiceImpl(CarRepository carRepository, CarCommandToCar carCommandToCar) {
        this.carRepository = carRepository;
        this.carCommandToCar = carCommandToCar;
    }


    @Override
    public Car save(CarCommand carCommand) {
        return carRepository.save(carCommandToCar.convert(carCommand));
    }
}
