package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Visit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VisitToVisitCommand implements Converter<Visit, VisitCommand> {

    @Override
    public VisitCommand convert(Visit source) {

        if (source == null) {
            return null;
        }

        final VisitCommand visitCommand = new VisitCommand();
        visitCommand.setId(source.getId());
        visitCommand.setDate(source.getDate());
        visitCommand.setDescription(source.getDescription());

        if (source.getCar() != null) {
            visitCommand.setCarId(source.getCar().getId());
        }

        return visitCommand;
    }
}
