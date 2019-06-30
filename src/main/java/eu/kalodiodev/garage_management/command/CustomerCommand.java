package eu.kalodiodev.garage_management.command;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CustomerCommand {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 192)
    private String name;

    private String address;
    private String city;
    private String postcode;
    private String country;
    private String phone;
    private String email;
    private String comment;
    private Set<CarCommand> cars = new HashSet<>();
}
