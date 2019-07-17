package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.VisitCommand;
import eu.kalodiodev.garage_management.domains.Car;
import eu.kalodiodev.garage_management.domains.Visit;
import eu.kalodiodev.garage_management.services.CarService;
import eu.kalodiodev.garage_management.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
        visitCommand.setCustomerId(customerId);

        model.addAttribute("visitCommand", visitCommand);

        return VIEW_VISIT_CREATE;
    }

    @PostMapping("/customers/{customerId}/cars/{carId}/visits")
    public String storeVisit(@PathVariable Long customerId,
                             @PathVariable Long carId,
                             @Valid VisitCommand visitCommand,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return VIEW_VISIT_CREATE;
        }

        Car car = carService.findByCustomerIdAndCarId(customerId, carId);
        visitCommand.setCarId(car.getId());
        Visit visit = visitService.save(visitCommand);

        redirectAttributes.addFlashAttribute("message", "Visit created");

        return "redirect:/customers/" + customerId + "/cars/" + carId + "/visits/" + visit.getId();
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}/edit")
    public String editVisit(@PathVariable Long customerId,
                            @PathVariable Long carId,
                            @PathVariable Long visitId,
                            Model model) {

        VisitCommand visitCommand =
                visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(customerId, carId, visitId);
        visitCommand.setCustomerId(customerId);

        model.addAttribute("visitCommand", visitCommand);

        return VIEW_VISIT_EDIT;
    }

    @PatchMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}")
    public String updateVisit(@PathVariable Long customerId,
                              @PathVariable Long carId,
                              @PathVariable Long visitId,
                              @Valid VisitCommand visitCommand,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        visitService.findVisitCommandByCustomerIdAndCarIdAndVisitId(customerId, carId, visitId);

        visitCommand.setId(visitId);
        visitCommand.setCarId(carId);
        visitCommand.setCustomerId(customerId);

        if (bindingResult.hasErrors()) {
            return VIEW_VISIT_EDIT;
        }

        visitService.update(visitCommand);

        redirectAttributes.addFlashAttribute("message", "Visit updated");

        return "redirect:/customers/" + customerId + "/cars/" + carId + "/visits/" + visitId;
    }

    @DeleteMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}")
    public String deleteVisit(@PathVariable Long customerId,
                              @PathVariable Long carId,
                              @PathVariable Long visitId,
                              RedirectAttributes redirectAttributes) {

        visitService.delete(customerId, carId, visitId);

        redirectAttributes.addFlashAttribute("message", "Visit deleted");

        return "redirect:/customers/" + customerId + "/cars/" + carId;
    }
}
