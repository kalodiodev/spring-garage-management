package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarCommandToCar implements Converter<CarCommand, Car> {

    @Override
    public Car convert(CarCommand source) {

        if (source == null) {
            return null;
        }

        final Car car = new Car();
        car.setId(source.getId());
        car.setNumberPlate(source.getNumberPlate());
        car.setManufacturer(source.getManufacturer());
        car.setManufacturedYear(source.getManufacturedYear());
        car.setModel(source.getModel());

        if (source.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setId(source.getCustomerId());
            car.setCustomer(customer);
            customer.getCars().add(car);
        }

        return car;
    }
}
