package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;

public interface CrudService<T, C, ID> {

    T save(C commandObject);

    T findById(ID id);

    C findCommandById(ID id);
}
