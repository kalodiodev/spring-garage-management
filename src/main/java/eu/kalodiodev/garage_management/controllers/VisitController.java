package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VisitController {

    private static final String VIEW_VISIT_SHOW = "visit/show";

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/customers/{customerId}/cars/{carId}/visits/{visitId}")
    public String showVisit(@PathVariable Long customerId,
                            @PathVariable Long carId,
                            @PathVariable Long visitId,
                            Model model) {

        model.addAttribute("visit", visitService.findById(visitId));

        return VIEW_VISIT_SHOW;
    }
}
