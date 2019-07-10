package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Visit;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
public class VisitController {

    private static final String VIEW_VISIT_SHOW = "visit/show";
    private static final String VIEW_VISIT_CREATE = "visit/create";
    private static final String VIEW_VISIT_EDIT = "visit/edit";

    private final VisitService visitService;
    private final CarService carService;

    public VisitController(VisitService visitService, CarService carService) {
        this.visitService = visitService;
        this.carService = carService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}")
    public String showVisit(@PathVariable Long customerId,
                            @PathVariable Long carId,
                            @PathVariable Long visitId,
                            Model model) {

        model.addAttribute("visit", visitService.findByCustomerIdAndCarIdAndVisitId(customerId, carId, visitId));
        model.addAttribute("customerId", customerId);
        model.addAttribute("carId", carId);

        return VIEW_VISIT_SHOW;
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/visits/create")
    public String createVisit(@PathVariable Long customerId,
                              @PathVariable Long carId,
                              Model model) {

        Car car = carService.findByCustomerIdAndCarId(customerId, carId);

        VisitCommand visitCommand = new VisitCommand();
        visitCommand.setCarId(car.getId());

        model.addAttribute("visitCommand", visitCommand);
        model.addAttribute("car", car);

        return VIEW_VISIT_CREATE;
    }

    @PostMapping("/customers/{customerId}/cars/{carId}/visits")
    public String storeVisit(@PathVariable Long customerId,
                             @PathVariable Long carId,
                             VisitCommand visitCommand) {

        Car car = carService.findByCustomerIdAndCarId(customerId, carId);

        visitCommand.setCarId(car.getId());

        Visit visit = visitService.save(visitCommand);

        return "redirect:/customers/" + customerId + "/cars/" + carId + "/visits/" + visit.getId();
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}/edit")
    public String editVisit(@PathVariable Long customerId,
                            @PathVariable Long carId,
                            @PathVariable Long visitId,
                            Model model) {

        VisitCommand visitCommand =
                visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(customerId, carId, visitId);

        model.addAttribute("visitCommand", visitCommand);
        model.addAttribute("customerId", customerId);

        return VIEW_VISIT_EDIT;
    }

    @PatchMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}")
    public String updateVisit(@PathVariable Long customerId,
                              @PathVariable Long carId,
                              @PathVariable Long visitId,
                              VisitCommand visitCommand) {

        visitCommand.setId(visitId);
        visitCommand.setCarId(carId);

        visitService.update(visitCommand);

        return "redirect:/customers/" + customerId + "/cars/" + carId + "/visits/" + visitId;
    }
}
