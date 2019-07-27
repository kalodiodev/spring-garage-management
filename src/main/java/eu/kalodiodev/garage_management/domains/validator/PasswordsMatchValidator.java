package eu.kalodiodev.garage_management.domains.validator;

import eu.kalodiodev.garage_management.command.UserCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordMatch, UserCommand> {

    @Override
    public boolean isValid(UserCommand userCommand, ConstraintValidatorContext constraintValidatorContext) {
        if (userCommand.getPassword() != null && userCommand.getPasswordConfirm() != null) {
            return userCommand.getPassword().equals(userCommand.getPasswordConfirm());
        }

        return false;
    }

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

    }
}
