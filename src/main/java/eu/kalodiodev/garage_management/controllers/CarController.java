package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CarCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CarController {

    private static final String VIEW_CAR_CREATE = "car/create";
    private static final String VIEW_CAR_EDIT = "car/edit";
    private static final String VIEW_CAR_SHOW = "car/show";

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

    @GetMapping("/customers/{customerId}/cars/{carId}")
    public String showCar(@PathVariable Long customerId, @PathVariable Long carId, Model model) {

        model.addAttribute("car", carService.findByCustomerIdAndCarId(customerId, carId));

        return VIEW_CAR_SHOW;
    }

    @GetMapping("/customers/{customerId}/cars/create")
    public String createCar(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        CarCommand carCommand = new CarCommand();
        carCommand.setCustomerId(customer.getId());

        model.addAttribute("carCommand", carCommand);

        return VIEW_CAR_CREATE;
    }

    @PostMapping("/customers/{customerId}/cars")
    public String storeCar(@PathVariable Long customerId,
                           @Valid CarCommand carCommand,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return VIEW_CAR_CREATE;
        }

        Customer customer = customerService.findById(customerId);
        carCommand.setCustomerId(customer.getId());

        carService.save(carCommand);

        redirectAttributes.addFlashAttribute("message", "Car stored successfully!");

        return "redirect:/customers/" + customerId;
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/edit")
    public String editCar(@PathVariable Long customerId, @PathVariable Long carId, Model model) {
        model.addAttribute("carCommand", carService.findCommandByCustomerIdAndCarId(customerId, carId));

        return VIEW_CAR_EDIT;
    }

    @PatchMapping("/customers/{customerId}/cars/{carId}")
    public String updateCar(@PathVariable Long customerId,
                            @PathVariable Long carId,
                            CarCommand carCommand,
                            RedirectAttributes redirectAttributes) {

        carCommand.setId(carId);
        carCommand.setCustomerId(customerId);
        carService.update(carCommand);

        redirectAttributes.addFlashAttribute("message", "Car updated successfully!");

        return "redirect:/customers/" + customerId;
    }

    @DeleteMapping("/customers/{customerId}/cars/{carId}")
    public String deleteCar(@PathVariable Long customerId,
                            @PathVariable Long carId,
                            RedirectAttributes redirectAttributes) {

        carService.delete(customerId, carId);

        redirectAttributes.addFlashAttribute("message", "Car deleted successfully!");

        return "redirect:/customers/" + customerId;
    }
}
