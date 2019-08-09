package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    private static final String EDIT_PROFILE_VIEW = "profile/edit";

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/profile")
    public String editProfile(Model model, @AuthenticationPrincipal User user) {

        UserInfoCommand userInfoCommand = new UserInfoCommand();
        userInfoCommand.setId(user.getId());
        userInfoCommand.setEmail(user.getEmail());
        userInfoCommand.setFirstName(user.getFirstName());
        userInfoCommand.setLastName(user.getLastName());


        model.addAttribute("userInfoCommand", userInfoCommand);

        return EDIT_PROFILE_VIEW;
    }

    @PatchMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                UserInfoCommand userInfoCommand,
                                RedirectAttributes redirectAttributes) {

        userService.updateUserInfo(user, userInfoCommand);

        redirectAttributes.addFlashAttribute("message", "Profile updated");

        return "redirect:/profile";
    }
}
