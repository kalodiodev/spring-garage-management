package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;

public interface CarService {

    Car save(CarCommand carCommand);

    Car findById(Long id);

    CarCommand findCommandById(Long id);
}
