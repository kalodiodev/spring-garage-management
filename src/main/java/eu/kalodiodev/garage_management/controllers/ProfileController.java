package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.PasswordCommand;
import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.converter.UserToUserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProfileController extends BaseUserController {

    private static final String EDIT_PROFILE_VIEW = "profile/edit";

    private final UserToUserInfoCommand userToUserInfoCommand;

    public ProfileController(UserService userService, UserToUserInfoCommand userToUserInfoCommand) {
        super(userService);
        this.userToUserInfoCommand = userToUserInfoCommand;
    }

    @RequestMapping("/profile")
    public String editProfile(Model model, @AuthenticationPrincipal User user) {

        UserInfoCommand userInfoCommand = userToUserInfoCommand.convert(user);

        model.addAttribute("userInfoCommand", userInfoCommand);
        model.addAttribute("passwordCommand", new PasswordCommand());

        return EDIT_PROFILE_VIEW;
    }

    @PatchMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @Valid UserInfoCommand userInfoCommand,
                                BindingResult bindingResult,
                                @ModelAttribute("passwordCommand")PasswordCommand passwordCommand,
                                RedirectAttributes redirectAttributes) {

        if (userInfoErrors(user, userInfoCommand.getEmail(), "userInfoCommand", bindingResult)) {
            return EDIT_PROFILE_VIEW;
        }

        userService.updateUserInfo(user, userInfoCommand);

        redirectAttributes.addFlashAttribute("message", "Profile updated");

        return "redirect:/profile";
    }

    @PatchMapping("/profile/password")
    public String updatePassword(@AuthenticationPrincipal User user,
                                 @Valid PasswordCommand passwordCommand,
                                 BindingResult bindingResult,
                                 @ModelAttribute("userInfoCommand")UserInfoCommand userInfoCommand,
                                 RedirectAttributes redirectAttributes) {

        if (passwordErrors(user, userInfoCommand, bindingResult)) {
            return EDIT_PROFILE_VIEW;
        }

        userService.updatePassword(user, passwordCommand);

        redirectAttributes.addFlashAttribute("message", "Password updated");

        return "redirect:/profile";
    }
}
