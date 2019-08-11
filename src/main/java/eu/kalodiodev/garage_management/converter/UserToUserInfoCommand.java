package eu.kalodiodev.garage_management.converter;

import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserInfoCommand  implements Converter<User, UserInfoCommand> {

    @Override
    public UserInfoCommand convert(User user) {
        if (user == null) {
            return null;
        }

        UserInfoCommand userInfoCommand = new UserInfoCommand();
        userInfoCommand.setId(user.getId());
        userInfoCommand.setEmail(user.getEmail());
        userInfoCommand.setFirstName(user.getFirstName());
        userInfoCommand.setLastName(user.getLastName());

        return userInfoCommand;
    }
}
