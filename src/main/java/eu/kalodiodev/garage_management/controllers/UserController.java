package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final String VIEW_USERS_INDEX = "user/index";
    private static final String VIEW_USER_SHOW = "user/show";
    private static final String VIEW_USER_CREATE = "user/create";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String indexUsers(Model model) {
        model.addAttribute("users", userService.all());

        return VIEW_USERS_INDEX;
    }

    @GetMapping("/users/{id}")
    public String showUser(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));

        return VIEW_USER_SHOW;
    }

    @GetMapping("/users/create")
    public String createUser(Model model) {
        model.addAttribute("userCommand", new UserCommand());

        return VIEW_USER_CREATE;
    }

    @PostMapping("/users")
    public String storeUser(@Valid UserCommand userCommand, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (userService.isEmailAlreadyInUse(userCommand.getEmail())) {
            FieldError fieldError = new FieldError("userCommand", "email", "Email already in use");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            return VIEW_USER_CREATE;
        }

        User user = userService.register(userCommand);

        redirectAttributes.addFlashAttribute("message", "User created");

        return "redirect:/users/" + user.getId();
    }
}
