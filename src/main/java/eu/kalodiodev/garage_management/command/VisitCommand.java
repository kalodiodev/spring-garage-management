package eu.kalodiodev.garage_management.command;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VisitCommand {

    private Long id;
    private LocalDate date;
    private String description;
    private Long carId;
}
