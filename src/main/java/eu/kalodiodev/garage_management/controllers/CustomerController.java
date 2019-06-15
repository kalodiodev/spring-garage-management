package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    private static final String VIEW_CUSTOMERS_INDEX = "customers/index";
    private static final String VIEW_CUSTOMER_CREATE = "customers/create";
    private static final String VIEW_CUSTOMER_SHOW = "customers/show";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.all());

        return VIEW_CUSTOMERS_INDEX;
    }

    @GetMapping("customers/{id}")
    public String showCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));

        return VIEW_CUSTOMER_SHOW;
    }

    @GetMapping("customers/create")
    public String createCustomer(Model model) {
        model.addAttribute("customerCommand", new CustomerCommand());

        return VIEW_CUSTOMER_CREATE;
    }

    @PostMapping("customers")
    public String storeCustomer(CustomerCommand customerCommand) {

        Customer customer = customerService.save(customerCommand);

        return "redirect:/customers/" + customer.getId();
    }
}
