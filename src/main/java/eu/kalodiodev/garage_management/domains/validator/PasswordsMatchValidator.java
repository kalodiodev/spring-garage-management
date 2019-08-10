package eu.kalodiodev.garage_management.domains.validator;

import eu.kalodiodev.garage_management.command.PasswordCommand;
import eu.kalodiodev.garage_management.command.UserCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordMatch, PasswordCommand> {

    @Override
    public boolean isValid(PasswordCommand passwordCommand, ConstraintValidatorContext constraintValidatorContext) {
        if (passwordCommand.getPassword() != null && passwordCommand.getPasswordConfirm() != null) {
            return passwordCommand.getPassword().equals(passwordCommand.getPasswordConfirm());
        }

        return false;
    }

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

    }
}
