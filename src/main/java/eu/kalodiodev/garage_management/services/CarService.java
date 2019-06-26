package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;

public interface CarService extends CrudService<Car, CarCommand, Long> {

    Car save(CarCommand carCommand);

    Car findById(Long id);

    CarCommand findCommandById(Long id);

    void update(CarCommand carCommand);
}
