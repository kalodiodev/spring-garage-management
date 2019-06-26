package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CarController {

    private static final String VIEW_CAR_CREATE = "car/create";
    private static final String VIEW_CAR_EDIT = "car/edit";

    private final CustomerService customerService;
    private final CarService carService;

    public CarController(CustomerService customerService, CarService carService) {
        this.customerService = customerService;
        this.carService = carService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("carCommand")
    public CarCommand loadCarWithCustomer(@PathVariable("customerId") Long customerId, Map<String, Object> model) {
        CustomerCommand customerCommand = customerService.findCommandById(customerId);
        model.put("customerCommand", customerCommand);
        CarCommand carCommand = new CarCommand();
        carCommand.setCustomerId(customerCommand.getId());

        return carCommand;
    }

    @GetMapping("/customers/{customerId}/cars/create")
    public String createCar() {
        return VIEW_CAR_CREATE;
    }

    @PostMapping("/customers/{customerId}/cars")
    public String storeCar(@PathVariable Long customerId, CarCommand carCommand) {

        carService.save(carCommand);

        return "redirect:/customers/" + customerId;
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/edit")
    public String editCar(@PathVariable Long carId, Model model) {
        model.addAttribute("carCommand", carService.findCommandById(carId));

        return VIEW_CAR_EDIT;
    }
}
