package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.domains.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    @Override
    public User convert(UserCommand userCommand) {
        if (userCommand == null) {
            return null;
        }

        final User user = new User();
        user.setId(userCommand.getId());
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        user.setEmail(userCommand.getEmail());
        user.setPassword(userCommand.getPassword());

        return user;
    }
}