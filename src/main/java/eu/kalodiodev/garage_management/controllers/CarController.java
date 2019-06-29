package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/customers/{customerId}/cars/create")
    public String createCar(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);
        model.addAttribute("carCommand", new CarCommand());

        return VIEW_CAR_CREATE;
    }

    @PostMapping("/customers/{customerId}/cars")
    public String storeCar(@PathVariable Long customerId, CarCommand carCommand) {
        Customer customer = customerService.findById(customerId);
        carCommand.setCustomerId(customer.getId());
        carService.save(carCommand);

        return "redirect:/customers/" + customerId;
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/edit")
    public String editCar(@PathVariable Long customerId, @PathVariable Long carId, Model model) {
        model.addAttribute("carCommand", carService.findCommandByCustomerIdAndCarId(customerId, carId));

        return VIEW_CAR_EDIT;
    }

    @PatchMapping("/customers/{customerId}/cars/{carId}")
    public String updateCar(@PathVariable Long customerId, @PathVariable Long carId, CarCommand carCommand) {
        carCommand.setId(carId);
        carCommand.setCustomerId(customerId);
        carService.update(carCommand);

        return "redirect:/customers/" + customerId;
    }

    @DeleteMapping("/customers/{customerId}/cars/{carId}")
    public String deleteCar(@PathVariable Long customerId, @PathVariable Long carId) {

        carService.delete(customerId, carId);

        return "redirect:/customers/" + customerId;
    }
}
