package eu.kalodiodev.garage_management.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

    private Long id;
    private String manufacturer;
    private String model;
    private int manufacturedYear;
    private String numberPlate;
}
