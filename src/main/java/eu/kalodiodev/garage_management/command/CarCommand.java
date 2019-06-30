package eu.kalodiodev.garage_management.command;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CarCommand {

    private Long id;

    @NotBlank
    @Size(min = 4, max = 90)
    private String numberPlate;

    @NotBlank
    @Size(min = 3, max = 100)
    private String manufacturer;

    @NotBlank
    @Size(min = 1, max = 100)
    private String model;

    private int manufacturedYear;
    private Long customerId;
}
