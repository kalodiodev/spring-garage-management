package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.NotFoundException;
import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.converter.CarCommandToCar;
import eu.kalodiodev.garage_management.converter.CarToCarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.repositories.CarRepository;
import eu.kalodiodev.garage_management.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JpaCarServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    CarCommandToCar carCommandToCar;

    @Mock
    CarToCarCommand carToCarCommand;

    @InjectMocks
    JpaCarServiceImpl carService;


    @BeforeEach
    void setUp() {

    }

    @Test
    void find_car_by_id() {
        Car car = new Car();
        car.setId(1L);
        Optional<Car> optionalCar = Optional.of(car);

        when(carRepository.findById(1L)).thenReturn(optionalCar);

        assertEquals(car, carService.findById(1L));
    }

    @Test
    void not_found_car() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.findById(1L));
    }

    @Test
    void find_car_command_by_id() {
        Car car = new Car();
        car.setId(1L);

        CarCommand carCommand = new CarCommand();
        carCommand.setId(1L);

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(carToCarCommand.convert(any(Car.class))).thenReturn(carCommand);

        assertEquals(carCommand, carService.findCommandById(1L));
    }

    @Test
    void not_found_car_command() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.findCommandById(1L));
    }

    @Test
    void update_car() {
        CarCommand carCommand = new CarCommand();
        carCommand.setId(1L);
        carCommand.setNumberPlate("AAA-2345");

        when(carRepository.findById(1L)).thenReturn(Optional.of(new Car()));
        when(carCommandToCar.convert(any(CarCommand.class))).thenReturn(new Car());

        carService.update(carCommand);

        verify(carRepository, times(1)).save(any(Car.class));
    }
}