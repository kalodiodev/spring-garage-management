package eu.kalodiodev.garage_management.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordCommand {

    private String password;
    private String passwordConfirm;
}
