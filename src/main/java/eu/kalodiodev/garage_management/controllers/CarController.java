package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CarCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarController {

    private static final String VIEW_CAR_CREATE = "car/create";

    @GetMapping("/customers/{customerId}/cars/create")
    public String createCar(Model model) {

        model.addAttribute("carCommand", new CarCommand());

        return VIEW_CAR_CREATE;
    }

}
