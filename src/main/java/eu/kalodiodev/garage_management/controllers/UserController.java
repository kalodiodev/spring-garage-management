package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.PasswordCommand;
import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final String VIEW_USERS_INDEX = "user/index";
    private static final String VIEW_USER_SHOW = "user/show";
    private static final String VIEW_USER_CREATE = "user/create";
    private static final String VIEW_USER_EDIT = "user/edit";

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

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);

        redirectAttributes.addFlashAttribute("message", "User deleted");

        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("userInfoCommand", userService.findInfoCommandById(id));
        model.addAttribute("passwordCommand", new PasswordCommand());

        return VIEW_USER_EDIT;
    }

    @PatchMapping("/users/{id}")
    public String updateUserInfo(@PathVariable Long id,
                                 @Valid UserInfoCommand userInfoCommand,
                                 BindingResult bindingResult,
                                 @ModelAttribute PasswordCommand passwordCommand,
                                 RedirectAttributes redirectAttributes) {

        User user = userService.findById(id);

        if (userService.isEmailAlreadyInUseExceptUser(userInfoCommand.getEmail(), user)) {
            FieldError fieldError = new FieldError("userInfoCommand", "email", userInfoCommand.getEmail(), false, null, null, "Email already in use");
            bindingResult.addError(fieldError);
        }

        if (bindingResult.hasErrors()) {
            return VIEW_USER_EDIT;
        }

        userService.updateUserInfo(user, userInfoCommand);

        redirectAttributes.addFlashAttribute("message", "User Info updated");

        return "redirect:/users/" + user.getId();
    }

    @PatchMapping("/users/{id}/password")
    public String updateUserPassword(@PathVariable Long id,
                                     @Valid PasswordCommand passwordCommand,
                                     BindingResult bindingResult,
                                     @ModelAttribute UserInfoCommand userInfoCommand,
                                     RedirectAttributes redirectAttributes) {

        User user = userService.findById(id);

        if (bindingResult.hasErrors()) {
            userInfoCommand.setId(user.getId());
            userInfoCommand.setEmail(user.getEmail());
            userInfoCommand.setFirstName(user.getFirstName());
            userInfoCommand.setLastName(user.getLastName());

            return VIEW_USER_EDIT;
        }

        userService.updatePassword(user, passwordCommand);

        redirectAttributes.addFlashAttribute("message", "Password updated");

        return "redirect:/users/" + user.getId();
    }
}
