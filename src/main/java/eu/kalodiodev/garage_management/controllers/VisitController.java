package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VisitController {

    private static final String VIEW_VISIT_SHOW = "visit/show";
    private static final String VIEW_VISIT_CREATE = "visit/create";

    private final VisitService visitService;
    private final CarService carService;

    public VisitController(VisitService visitService, CarService carService) {
        this.visitService = visitService;
        this.carService = carService;
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}")
    public String showVisit(@PathVariable Long customerId,
                            @PathVariable Long carId,
                            @PathVariable Long visitId,
                            Model model) {

        model.addAttribute("visit", visitService.findById(visitId));

        return VIEW_VISIT_SHOW;
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/visits/create")
    public String createVisit(@PathVariable Long customerId, @PathVariable Long carId, Model model) {
        Car car = carService.findByCustomerIdAndCarId(customerId, carId);

        VisitCommand visitCommand = new VisitCommand();
        visitCommand.setCarId(car.getId());

        model.addAttribute("visitCommand", visitCommand);
        model.addAttribute("car", car);

        return VIEW_VISIT_CREATE;
    }
}
