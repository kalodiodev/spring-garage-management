package eu.kalodiodev.garage_management.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

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
