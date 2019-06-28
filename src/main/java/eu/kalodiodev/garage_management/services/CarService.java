package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;

public interface CarService extends CrudService<Car, CarCommand, Long> {

    void delete(Long customerId, Long carId);

}
