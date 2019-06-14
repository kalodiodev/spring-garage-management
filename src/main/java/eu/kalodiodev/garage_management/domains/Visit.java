package eu.kalodiodev.garage_management.domains;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Visit {

    private Long id;
    private LocalDate date;
    private String description;

}
