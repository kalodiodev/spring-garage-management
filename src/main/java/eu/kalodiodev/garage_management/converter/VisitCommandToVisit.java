package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Visit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VisitCommandToVisit implements Converter<VisitCommand, Visit> {

    @Override
    public Visit convert(VisitCommand source) {

        if (source == null) {
            return null;
        }

        final Visit visit = new Visit();
        visit.setId(source.getId());
        visit.setDate(source.getDate());
        visit.setDescription(source.getDescription());

        if (source.getCarId() != null) {
            Car car = new Car();
            car.setId(source.getCarId());
            car.getVisits().add(visit);
            visit.setCar(car);
        }

        return visit;
    }
}
