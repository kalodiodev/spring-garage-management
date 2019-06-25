package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Car;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarToCarCommand  implements Converter<Car, CarCommand> {

    @Override
    public CarCommand convert(Car source) {

        if (source == null) {
            return null;
        }

        CarCommand carCommand = new CarCommand();
        carCommand.setId(source.getId());
        carCommand.setNumberPlate(source.getNumberPlate());
        carCommand.setManufacturer(source.getManufacturer());
        carCommand.setManufacturedYear(source.getManufacturedYear());
        carCommand.setModel(source.getModel());

        if (source.getCustomer() != null) {
            carCommand.setCustomerId(source.getCustomer().getId());
        }

        return carCommand;
    }
}
