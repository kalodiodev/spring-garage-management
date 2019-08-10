package eu.kalodiodev.garage_management.command;

import eu.kalodiodev.garage_management.domains.validator.PasswordMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@PasswordMatch
public class PasswordCommand {

    @NotNull
    @Size(min = 8, max = 100)
    protected String password;

    @NotNull
    @Size(min = 8, max = 100)
    protected String passwordConfirm;
}
