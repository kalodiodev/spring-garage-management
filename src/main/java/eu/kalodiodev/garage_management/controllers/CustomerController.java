package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.CustomerCommand;
import eu.kalodiodev.garage_management.domains.Customer;
import eu.kalodiodev.garage_management.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CustomerController {

    private static final String VIEW_CUSTOMERS_INDEX = "customers/index";
    private static final String VIEW_CUSTOMER_CREATE = "customers/create";
    private static final String VIEW_CUSTOMER_SHOW = "customers/show";
    private static final String VIEW_CUSTOMER_EDIT = "customers/edit";

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
    public String storeCustomer(@Valid CustomerCommand customerCommand,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return VIEW_CUSTOMER_CREATE;
        }

        Customer customer = customerService.save(customerCommand);

        redirectAttributes.addFlashAttribute("message", "Customer Stored");

        return "redirect:/customers/" + customer.getId();
    }

    @GetMapping("customers/{id}/edit")
    public String editCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customerCommand", customerService.findCommandById(id));

        return VIEW_CUSTOMER_EDIT;
    }

    @PatchMapping("customers/{id}")
    public String updateCustomer(@PathVariable Long id,
                                 @Valid CustomerCommand customerCommand,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return VIEW_CUSTOMER_EDIT;
        }

        customerCommand.setId(id);
        customerService.update(customerCommand);

        redirectAttributes.addFlashAttribute("message", "Customer Updated");

        return "redirect:/customers/" + id;
    }

    @DeleteMapping("customers/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.delete(id);

        redirectAttributes.addFlashAttribute("message", "Customer deleted");

        return "redirect:/customers";
    }
}
