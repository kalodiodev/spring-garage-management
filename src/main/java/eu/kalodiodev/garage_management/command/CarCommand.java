package eu.kalodiodev.garage_management.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarCommand {

    private Long id;
    private String manufacturer;
    private String model;
    private int manufacturedYear;
    private String numberPlate;

}
