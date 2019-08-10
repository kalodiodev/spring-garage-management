package eu.kalodiodev.garage_management.services;

import eu.kalodiodev.garage_management.command.PasswordCommand;
import eu.kalodiodev.garage_management.command.UserCommand;
import eu.kalodiodev.garage_management.command.UserInfoCommand;
import eu.kalodiodev.garage_management.domains.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> all();

    User save(User user);

    User register(UserCommand command);

    boolean isEmailAlreadyInUse(String email);

    boolean isEmailAlreadyInUseExceptUser(String email, User user);

    void delete(Long id);

    User updateUserInfo(User user, UserInfoCommand userInfoCommand);

    User updatePassword(User user, PasswordCommand passwordCommand);
}
