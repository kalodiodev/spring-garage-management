package eu.kalodiodev.garage_management.command;

import eu.kalodiodev.garage_management.domains.validator.PasswordMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@PasswordMatch
public class UserCommand {

    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    @NotNull
    @Size(min = 8, max = 100)
    private String passwordConfirm;
}
