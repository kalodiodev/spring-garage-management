package eu.kalodiodev.garage_management.services;


public interface CrudService<T, C, ID> {

    T save(C commandObject);

    T findById(ID id);

    C findCommandById(ID id);
}
