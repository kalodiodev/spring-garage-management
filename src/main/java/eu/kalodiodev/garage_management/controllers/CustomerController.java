package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    private static final String VIEW_CUSTOMERS_INDEX = "customers/index";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.all());

        return VIEW_CUSTOMERS_INDEX;
    }
}
