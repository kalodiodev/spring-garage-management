package eu.kalodiodev.garage_management.services.jpa;

import eu.kalodiodev.garage_management.exceptions.NotFoundException;
import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.converter.CarCommandToCar;
import eu.kalodiodev.garage_management.converter.CarToCarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.repositories.CarRepository;
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
    CarRepository carRepository;

    @Mock
    CarCommandToCar carCommandToCar;

    @Mock
    CarToCarCommand carToCarCommand;

    @InjectMocks
    JpaCarServiceImpl carService;

    private Car car;
    private static final Long CAR_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;


    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        car = new Car();
        car.setId(CAR_ID);
        car.setCustomer(customer);
    }

    @Test
    void find_car_by_id() {
        when(carRepository.findById(CAR_ID)).thenReturn(Optional.of(car));

        assertEquals(car, carService.findById(CAR_ID));
    }

    @Test
    void not_found_car() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.findById(CAR_ID));
    }

    @Test
    void find_car_command_by_id() {
        CarCommand carCommand = new CarCommand();
        carCommand.setId(CAR_ID);

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(carToCarCommand.convert(any(Car.class))).thenReturn(carCommand);

        assertEquals(carCommand, carService.findCommandById(CAR_ID));
    }

    @Test
    void not_found_car_command() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.findCommandById(CAR_ID));
    }

    @Test
    void find_car_by_customer_id_and_car_id() {
        when(carRepository.findCarByIdAndCustomerId(CAR_ID, CUSTOMER_ID)).thenReturn(Optional.of(car));

        assertEquals(car, carService.findByCustomerIdAndCarId(CUSTOMER_ID, CAR_ID));
    }

    @Test
    void not_found_car_by_id_and_customer_id() {
        when(carRepository.findCarByIdAndCustomerId(CAR_ID, CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.findByCustomerIdAndCarId(CUSTOMER_ID, CAR_ID));
    }

    @Test
    void find_car_command_by_customer_id_and_car_id() {
        CarCommand carCommand = new CarCommand();
        carCommand.setId(CAR_ID);

        when(carRepository.findCarByIdAndCustomerId(CAR_ID, CUSTOMER_ID)).thenReturn(Optional.of(car));
        when(carToCarCommand.convert(any(Car.class))).thenReturn(carCommand);

        assertEquals(carCommand, carService.findCommandByCustomerIdAndCarId(CUSTOMER_ID, CAR_ID));
    }

    @Test
    void not_found_car_command_by_id_and_customer_id() {
        when(carRepository.findCarByIdAndCustomerId(CAR_ID, CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carService.findCommandByCustomerIdAndCarId(CUSTOMER_ID, CAR_ID));
    }

    @Test
    void update_car() {
        CarCommand carCommand = new CarCommand();
        carCommand.setId(CAR_ID);
        carCommand.setNumberPlate("AAA-2345");

        when(carRepository.findById(CAR_ID)).thenReturn(Optional.of(car));
        when(carCommandToCar.convert(any(CarCommand.class))).thenReturn(car);

        carService.update(carCommand);

        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void delete_car() {
        when(carRepository.findCarByIdAndCustomerId(CAR_ID, CUSTOMER_ID)).thenReturn(Optional.of(car));

        carService.delete(CUSTOMER_ID, CAR_ID);

        verify(carRepository, times(1)).delete(car);
    }
}