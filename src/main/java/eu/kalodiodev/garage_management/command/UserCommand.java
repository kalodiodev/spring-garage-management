package eu.kalodiodev.garage_management.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCommand {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String password_confirm;
}
