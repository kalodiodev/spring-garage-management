package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;

public interface CarService extends CrudService<Car, CarCommand, Long> {

    void delete(Long customerId, Long carId);

    Car findByCustomerIdAndCarId(Long customerId, Long carId);

    CarCommand findCommandByCustomerIdAndCarId(Long customerId, Long carId);
}
