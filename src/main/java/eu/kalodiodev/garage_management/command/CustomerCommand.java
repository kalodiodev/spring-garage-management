package eu.kalodiodev.garage_management.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCommand {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String postcode;
    private String country;
    private String phone;
    private String email;
    private String comment;
}