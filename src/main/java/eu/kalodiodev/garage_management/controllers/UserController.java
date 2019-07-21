package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private static final String VIEW_USERS_INDEX = "user/index";
    private static final String VIEW_USER_SHOW = "user/show";

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
}
