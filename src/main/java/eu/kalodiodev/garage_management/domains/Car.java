package eu.kalodiodev.garage_management.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "manufactured_year")
    private int manufacturedYear;

    @Column(name = "number_plate")
    private String numberPlate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
