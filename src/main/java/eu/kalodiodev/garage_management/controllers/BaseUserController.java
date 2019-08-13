package eu.kalodiodev.garage_management.controllers;

import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;
import eu.kalodiodev.garage_management.services.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Abstract Base User Controller
 *
 * Controllers that manage users should extend this class
 */
abstract class BaseUserController {

    protected final UserService userService;

    /**
     * Base User Controller constructor
     *
     * @param userService user service
     */
    public BaseUserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Process Password errors
     *
     * @param user user to be updated
     * @param userInfoCommand user info command
     * @param bindingResult binding result
     * @return true if it has errors otherwise false
     */
    boolean passwordErrors(User user, UserInfoCommand userInfoCommand, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            userInfoCommand.setId(user.getId());
            userInfoCommand.setEmail(user.getEmail());
            userInfoCommand.setFirstName(user.getFirstName());
            userInfoCommand.setLastName(user.getLastName());

            return true;
        }

        return false;
    }

    /**
     * User info errors, when creating new user
     *
     * @param email new email
     * @param commandObject submitted command object
     * @param bindingResult binding result
     * @return true if it has errors otherwise false
     */
    boolean userInfoErrors(String email, String commandObject, BindingResult bindingResult) {
        return userInfoErrors(null, email, commandObject, bindingResult);
    }

    /**
     * User info errors
     *
     * @param user user to be updated, null when creating new user
     * @param email new email
     * @param commandObject submitted command object
     * @param bindingResult binding result
     * @return true if it has errors otherwise false
     */
    boolean userInfoErrors(User user, String email, String commandObject, BindingResult bindingResult) {

        boolean emailInUse;

        if (user == null) {
            emailInUse = userService.isEmailAlreadyInUse(email);
        } else {
            emailInUse = userService.isEmailAlreadyInUseExceptUser(email, user);
        }

        if (emailInUse) {
            FieldError fieldError = new FieldError(commandObject, "email", email, false, null, null, "Email already in use");
            bindingResult.addError(fieldError);
        }

        return bindingResult.hasErrors();
    }
}
